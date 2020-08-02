package com.task.bot.сontroller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.task.bot.answerModel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import static com.task.bot.service.BotService.*;

@Controller
public class BotController {

    //Переменные из конфигурационного файла
    @Value("${token}")
    private String accessToken;

    @Value("${api.version}")
    private String apiVersion;

    @Value("${api.confirmation}")
    private String confirmationCode;

    @RequestMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String botCitation(@RequestBody String message) {

        JsonObject jsonRoot;
        String jsonType;
        Map<String, String> parameters;
        Message messageAnswer;
        MediaComment comment;

        if (message != null) {
            jsonRoot = JsonParser.parseString(message).getAsJsonObject();
            jsonType = getJsonType(jsonRoot);

            //Подтверждение сервера в CallbackApi
            if (jsonType.equals("confirmation")) {
                return confirmationCode;
            }

            //Сообщения сообщества и сообщения из бесед
            else if (jsonType.equals("message_new")) {

                messageAnswer = new Message(jsonRoot);

                parameters = new HashMap<>();
                parameters.put(tokenKey, accessToken);
                parameters.put(versionKey, apiVersion);
                parameters.put(peerKey, messageAnswer.getPeerId());
                parameters.put(messageKey, messageAnswer.getText());
                parameters.put("random_id", "0");
                postText("messages.send", parameters);

            }

            //Сообщения в комментариях и на стене сообщества
            else if ((jsonType.equals("wall_reply_new") || jsonType.equals("wall_post_new")) && isMention(jsonRoot)) {

                comment = new MediaComment(jsonRoot);

                parameters = new HashMap<>();
                parameters.put(tokenKey, accessToken);
                parameters.put(versionKey, apiVersion);
                parameters.put(ownerKey, comment.getOwnerId());
                parameters.put(postKey, comment.getMediaId());
                parameters.put("reply_to_comment", comment.getCommentId());
                parameters.put(messageKey, comment.getText());
                postText("wall.createComment", parameters);

            }
        }
        return "ok";
    }



}
