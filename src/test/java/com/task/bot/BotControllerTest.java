package com.task.bot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application.properties")
public class BotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${bot.confirmation}")
    private String confirmation;

    @Test
    public void apiBadRequests() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void apiConfirmation() throws Exception {
        String json = "{ \"type\": \"confirmation\", \"group_id\": 197567162 }\n";
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(confirmation)));
    }

    @Test
    public void newMessage() throws Exception {
        String json = "{\"type\":\"message_new\"," +
                "\"object\":{\"message\":{\"date\":1596586768," +
                "\"from_id\":53290373,\"id\":172,\"peer_id\":53290373," +
                "\"text\":\"Сообщение боту\"}}," +
                "\"group_id\":197567162}\n";
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("ok")));
    }

    @Test
    public void postWithMention() throws Exception {
        String json = "{\"type\":\"wall_post_new\"," +
                "\"object\":{\"id\":385,\"from_id\":53290373," +
                "\"owner_id\":-197567162," +
                "\"text\":\"[club100|test] Сообщение на стене группы с упоминанием сообщества\"}" +
                ",\"group_id\":100}\n";
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("ok")));
    }

    @Test
    public void postWithoutMention() throws Exception {
        String json = "{\"type\":\"wall_post_new\"," +
                "\"object\":{\"id\":385,\"from_id\":53290373," +
                "\"owner_id\":-197567162," +
                "\"text\":\"Сообщение на стене группы без упоминания сообщества\"}" +
                ",\"group_id\":100}\n";
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("ok")));
    }

    @Test
    public void commentWithMention() throws Exception {
        String json = "{\"type\":\"wall_reply_new\"," +
                "\"object\":{\"id\":387,\"from_id\":53290373," +
                "\"post_id\":386,\"owner_id\":-197567162," +
                "\"text\":\"[club100|test] Коментарий под постом на стене с упоминанием сообщества\"}," +
                "\"group_id\":100}\n";
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("ok")));
    }

    @Test
    public void commentWithoutMention() throws Exception {
        String json = "{\"type\":\"wall_reply_new\"," +
                "\"object\":{\"id\":388,\"from_id\":53290373," +
                "\"post_id\":385,\"owner_id\":-197567162," +
                "\"text\":\"Коментарий под постом на стене без упоминания сообщества\"}," +
                "\"group_id\":197567162}\n";
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("ok")));
    }
}
