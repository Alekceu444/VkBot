package com.task.bot.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BotService {

    public static final String groupKey = "group_id";
    public static final String textKey = "text";
    public static final String tokenKey = "access_token";
    public static final String versionKey = "v";
    public static final String messageKey = "message";
    public static final String peerKey = "peer_id";
    public static final String ownerKey = "owner_id";
    public static final String postKey = "post_id";
    public static final String idKey = "id";
    public static final String objectKey = "object";
    public static final String typeKey = "type";
    public static final String userKey = "from_id";
    public static final String statusOk = "ok";

    private final CloseableHttpClient httpClient;

    public BotService(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    //Отправка ответного сообщения
    public void postText(String method, Map<String, String> parameters) {

        HttpGet get;

        try {
            URIBuilder builder = new URIBuilder("https://api.vk.com/method/" + method);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }

            get = new HttpGet(builder.build());
            CloseableHttpResponse httpResponse = httpClient.execute(get);
            httpResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Проверка упоминания сообщества в сообщениях на стене
    public static boolean isMention(String text, String groupId) {
        return text.matches("(.*)\\[club" + groupId + "\\|(.*?)\\](.*)");
    }

    //Составление текста для ответного сообщения бота
    public static String makeMessage(String userId, String text, String groupId) {
        String citation = text
                .replaceAll("\\[club" + groupId + "\\|(.*?)\\]\\s?", "");
        return "@" + idKey + userId + " , Вы написали мне: \"" + citation + "\"";
    }
}
