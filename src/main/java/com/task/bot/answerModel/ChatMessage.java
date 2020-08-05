package com.task.bot.answerModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import static com.task.bot.service.BotService.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage implements Message {

    private String text;
    private String peerId;

    public ChatMessage(
            @JsonProperty(objectKey) Map<String, Object> object,
            @JsonProperty(groupKey) String groupId
    ) {

        Map<String, Object> message = (Map<String, Object>) object.get(messageKey);

        this.text = makeMessage(message.get(userKey).toString(), message.get(textKey).toString(), groupId);
        this.peerId = message.get(peerKey).toString();
    }

    public String getText() {
        return text;
    }

    public String getPeerId() {
        return peerId;
    }

}
