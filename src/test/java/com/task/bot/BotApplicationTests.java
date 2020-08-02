package com.task.bot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.task.bot.service.BotService.*;

@SpringBootTest
class BotApplicationTests {

    @Test
    void withMentionCheck() {

        String withMention = "{\"type\":\"wall_reply_new\",\"object\":{\"id\":356," +
                "\"text\":\"[club197567162|Бот группы] Проверка упоминания\"},\"group_id\":197567162}\n";
        JsonObject JO;
        JO = JsonParser.parseString(withMention).getAsJsonObject();
        Assertions.assertTrue(isMention(JO));
    }
    @Test
    void withoutMentionCheck() {

        JsonObject JO;
        String withoutMention = "{\"type\":\"message_new\",\"object\":{\"text\":\" Сообщение без упоминания сообщества \"}," +
                "\"group_id\":197567162}\n";
        JO = JsonParser.parseString(withoutMention).getAsJsonObject();
        Assertions.assertTrue(!isMention(JO));
    }
    @Test
    void fakeMentionCheck() {

        JsonObject JO;
        String withFakeMention = "{\"type\":\"message_new\",\"object\":{\"text\":\"[club1975673262| Бот другой группы]" +
                " Проверка на упоминание\"},\"group_id\":197567162}\n";
        JO = JsonParser.parseString(withFakeMention).getAsJsonObject();
        Assertions.assertTrue(!isMention(JO));
    }

    @Test
    void jsonTypeCheckCorrect() {
        JsonObject JO;
        String json = "{\"type\":\"testedType\",\"group_id\":197567162}\n";
        JO = JsonParser.parseString(json).getAsJsonObject();
        System.out.println(getJsonType(JO));
        Assertions.assertEquals(getJsonType(JO),"testedType");
    }
    @Test
    void jsonTypeCheckIncorrect() {
        JsonObject JO;
        String json = "{\"group_id\":197567162}\n";
        JO = JsonParser.parseString(json).getAsJsonObject();
        System.out.println(getJsonType(JO));
        Assertions.assertEquals(getJsonType(JO),"");
    }

    @Test()
    void makeMessageCorrectJson() {
        String json = "{\"type\":\"message_new\",\"object\":{" +
                "\"message\":{\"from_id\":53290373,\"text\":\"Проверка создания сообщения при корректном Json.\"}}" +
                ",\"group_id\":197567162}\n";
        JsonObject JO;
        JO = JsonParser.parseString(json).getAsJsonObject();
        JsonObject childJO = JO.getAsJsonObject(objectKey).getAsJsonObject(messageKey);
        Assertions.assertEquals(makeMessage(JO,childJO),"@id53290373 , Вы написали мне: \"Проверка создания сообщения при корректном Json.\"");
    }
    @Test()
    void makeMessageIncorrectJson() {
        String json = "{\"type\":\"message_new\",\"object\":{" +
                "\"message\":{\"from_id\":53290373}}}\n";
        JsonObject JO;
        JO = JsonParser.parseString(json).getAsJsonObject();
        JsonObject childJO = JO.getAsJsonObject(objectKey).getAsJsonObject(messageKey);
        Assertions.assertEquals(makeMessage(JO,childJO),"");
    }
    @Test()
    void makeCommentCorrectJson() {
        String json = "{\"type\":\"wall_reply_new\",\"object\":{\"from_id\":53290373," +
                "\"text\":\"[club197567162|Бот группы] Проверка корректности формирования" +
                " сообщения для коментария\"},\"group_id\":197567162}\n";
        JsonObject JO;
        JO = JsonParser.parseString(json).getAsJsonObject();
        JsonObject childJO = JO.getAsJsonObject(objectKey);
        Assertions.assertEquals(makeMessage(JO,childJO),"@id53290373 , Вы написали мне: \"Проверка корректности формирования сообщения для коментария\"");
    }
    @Test()
    void makeCommentIncorrectJson() {
        String json = "{\"type\":\"wall_reply_new\"," +
                "\"object\":{\"from_id\":53290373}}\n";
        JsonObject JO;
        JO = JsonParser.parseString(json).getAsJsonObject();
        JsonObject childJO = JO.getAsJsonObject(objectKey);
        Assertions.assertEquals(makeMessage(JO,childJO),"");

    }

}
