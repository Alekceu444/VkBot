package com.task.bot;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.task.bot.service.BotService.isMention;
import static com.task.bot.service.BotService.makeMessage;

@SpringBootTest
class BotMethodsTests {

    @Test
    void withMentionCheck() {
        String withMention = "[club100|Бот группы] Проверка упоминания";
        String groupId = "100";
        Assertions.assertTrue(isMention(withMention, groupId));
    }

    @Test
    void withoutMentionCheck() {

        String withMention = "Сообщение без упоминания сообщества";
        String groupId = "100";
        Assertions.assertFalse(isMention(withMention, groupId));
    }

    @Test
    void fakeMentionCheck() {

        String withMention = "[club200|Бот группы] Проверка упоминания";
        String groupId = "100";
        Assertions.assertFalse(isMention(withMention, groupId));
    }

    @Test()
    void makeMessageWithCitation() {
        String userId = "15";
        String text = "[club100|Бот группы] Проверка создания сообщения при корректном Json.";
        String groupId = "100";
        Assertions.assertEquals(makeMessage(userId, text, groupId),
                "@id15 , Вы написали мне: \"Проверка создания сообщения при корректном Json.\"");
    }

    @Test()
    void makeMessageWithoutCitation() {
        String userId = "15";
        String text = "Проверка создания сообщения при корректном Json.";
        String groupId = "100";
        Assertions.assertEquals(makeMessage(userId, text, groupId),
                "@id15 , Вы написали мне: \"Проверка создания сообщения при корректном Json.\"");
    }

    @Test()
    void makeMessageWithFakeCitation() {
        String userId = "15";
        String text = "[club200|Бот группы] Проверка создания сообщения при корректном Json.";
        String groupId = "100";
        Assertions.assertEquals(makeMessage(userId, text, groupId),
                "@id15 , Вы написали мне: \"[club200|Бот группы] Проверка создания сообщения при корректном Json.\"");
    }

}
