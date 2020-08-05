package com.task.bot.сontroller;

import com.task.bot.answerModel.ChatMessage;
import com.task.bot.answerModel.ConfirmationMessage;
import com.task.bot.answerModel.MediaComment;
import com.task.bot.answerModel.Message;
import com.task.bot.config.BotProperties;
import com.task.bot.service.BotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.task.bot.service.BotService.*;

@RestController
public class BotController {

    private final BotProperties botProperties;

    private final BotService service;

    public BotController(BotService service, BotProperties botProperties) {
        this.service = service;
        this.botProperties = botProperties;
    }

    @RequestMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public String botCitation(@RequestBody Message message) {

        Map<String, String> parameters;
        ChatMessage chatMessageAnswer;
        MediaComment comment;

        //Подтверждение сервера в CallbackApi
        if (message instanceof ConfirmationMessage) {
            return botProperties.getConfirmation();
        }

        //Сообщения сообщества и сообщения из бесед
        else if (message instanceof ChatMessage) {
            chatMessageAnswer = (ChatMessage) message;
            parameters = new HashMap<>();
            parameters.put(tokenKey, botProperties.getToken());
            parameters.put(versionKey, botProperties.getApiVersion());
            parameters.put(peerKey, chatMessageAnswer.getPeerId());
            parameters.put(messageKey, chatMessageAnswer.getText());
            parameters.put("random_id", "0");
            service.postText("messages.send", parameters);

        }

        //Сообщения в комментариях и на стене сообщества
        else if (message instanceof MediaComment) {

            comment = (MediaComment) message;
            if (!comment.isMentioned())
                return statusOk;
            parameters = new HashMap<>();
            parameters.put(tokenKey, botProperties.getToken());
            parameters.put(versionKey, botProperties.getApiVersion());
            parameters.put(ownerKey, comment.getOwnerId());
            parameters.put(postKey, comment.getMediaId());
            parameters.put("reply_to_comment", comment.getCommentId());
            parameters.put(messageKey, comment.getText());
            service.postText("wall.createComment", parameters);

        }
        return statusOk;
    }


}
