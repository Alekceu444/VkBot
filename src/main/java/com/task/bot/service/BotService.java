package com.task.bot.service;

import com.google.gson.JsonObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Map;

public class BotService {

    public static final String groupKey = "group_id";
    public static final String tokenKey = "access_token";
    public static final String versionKey = "v";
    public static final String messageKey = "message";
    public static final String peerKey = "peer_id";
    public static final String ownerKey = "owner_id";
    public static final String postKey = "post_id";
    public static final String idKey = "id";
    public static final String objectKey = "object";
    public static final String typeKey = "type";

    //Извлечение типа Json
    public static String getJsonType(JsonObject jsonRoot) {
        if (jsonRoot.has(typeKey))
            return jsonRoot.get(typeKey).getAsString();
        else
            return "";
    }

    //Отправка ответного сообщения
    public static void postText(String method, Map<String, String> parameters) {
        HttpClient client;
        HttpGet get;

        try {
            URIBuilder builder = new URIBuilder("https://api.vk.com/method/" + method);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }

            get = new HttpGet(builder.build());
            client = HttpClientBuilder.create()
                    .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();

            client.execute(get);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Проверка упоминания сообщества в сообщениях на стене
    public static boolean isMention( JsonObject jsonRoot) {
        String groupId = jsonRoot.get(groupKey).getAsString();
        JsonObject childJsonObject = jsonRoot.getAsJsonObject(objectKey);
        String text = childJsonObject.get("text") .getAsString();
        return text.matches("(.*)\\[club" + groupId + "\\|(.*?)\\](.*)");
    }

    //Составление текста для ответного сообщения бота
    public static String makeMessage(JsonObject jsonRoot, JsonObject childJsonObject){
        try {
            String userId = childJsonObject.get("from_id").getAsString();
            String groupId = jsonRoot.get(groupKey).getAsString();
            String citation = childJsonObject.get("text").getAsString()
                    .replaceAll("\\[club" + groupId + "\\|(.*?)\\]\\s?", "");
            return "@id" + userId + " , Вы написали мне: \"" + citation + "\"";
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
