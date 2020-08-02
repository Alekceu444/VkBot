package com.task.bot.answerModel;

import com.google.gson.JsonObject;

import static com.task.bot.service.BotService.*;

public class MediaComment {

    private String text;
    private String commentId;
    private String ownerId;
    private String mediaId;

    public MediaComment(JsonObject jsonRoot){

        JsonObject childJsonObject = jsonRoot.getAsJsonObject(objectKey);

        this.text = makeMessage(jsonRoot, childJsonObject);
        this.ownerId = childJsonObject.get(ownerKey).getAsString();

        //Опубликован пост или это комментарий под ранее созданным постом
        if (childJsonObject.has(postKey)) {
            this.mediaId = childJsonObject.get(postKey).getAsString();
            this.commentId = childJsonObject.get(idKey).getAsString();
        }
        else {
            this.mediaId = childJsonObject.get(idKey).getAsString();
            this.commentId = "0";
        }

    }

    public String getText() {
        return text;
    }

    public String getCommentId() {
        return commentId;
    }
    public String getOwnerId() {
        return ownerId;
    }
    public String getMediaId() {
        return mediaId;
    }
}
