package com.task.bot.answerModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import static com.task.bot.service.BotService.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaComment implements Message {

    private String text;
    private String commentId;
    private String ownerId;
    private String mediaId;
    private Boolean mention;

    public MediaComment(
            @JsonProperty(objectKey) Map<String, Object> object,
            @JsonProperty(groupKey) String groupId
    ) {

        String originalText = object.get(textKey).toString();
        this.text = makeMessage(object.get(userKey).toString(), originalText, groupId);
        this.ownerId = object.get(ownerKey).toString();
        this.mention = isMention(originalText, groupId);

        //Опубликован пост или это комментарий под ранее созданным постом
        if (object.containsKey(postKey)) {
            this.mediaId = object.get(postKey).toString();
            this.commentId = object.get(idKey).toString();
        } else {
            this.mediaId = object.get(idKey).toString();
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

    public Boolean isMentioned() {
        return mention;
    }
}
