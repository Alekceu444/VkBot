package com.task.bot.answerModel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.task.bot.service.BotService.typeKey;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = typeKey, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChatMessage.class, name = "message_new"),
        @JsonSubTypes.Type(value = MediaComment.class, name = "wall_reply_new"),
        @JsonSubTypes.Type(value = MediaComment.class, name = "wall_post_new"),
        @JsonSubTypes.Type(value = ConfirmationMessage.class, name = "confirmation")
})
public interface Message {

}
