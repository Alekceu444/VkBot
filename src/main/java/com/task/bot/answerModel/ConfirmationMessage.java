package com.task.bot.answerModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.task.bot.service.BotService.groupKey;
import static com.task.bot.service.BotService.typeKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfirmationMessage implements Message {

    private String type;
    private String groupId;

    public ConfirmationMessage(
            @JsonProperty(typeKey) String type,
            @JsonProperty(groupKey) String groupId
    ) {
        this.type = type;
        this.groupId = groupId;
    }

}
