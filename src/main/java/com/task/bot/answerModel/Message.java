package com.task.bot.answerModel;

import com.google.gson.JsonObject;
import static com.task.bot.service.BotService.*;

public class Message {

    private String text;
    private String peerId;

    public Message(JsonObject jsonRoot){

        JsonObject childJsonObject = jsonRoot.getAsJsonObject(objectKey).getAsJsonObject(messageKey);

        this.text = makeMessage(jsonRoot, childJsonObject);
        this.peerId = childJsonObject.get(peerKey).getAsString();
    }

    public String getText() {
        return text;
    }

    public String getPeerId() {
        return peerId;
    }

}
