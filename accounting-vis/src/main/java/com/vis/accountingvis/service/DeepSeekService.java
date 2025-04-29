package com.vis.accountingvis.service;

import com.vis.accountingvis.dto.ChatRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeepSeekService {

    @Value("${deepseek.api.url}")
    private String deepSeekApiUrl; // 你的 DeepSeek 请求地址

    @Value("${deepseek.api.key}")
    private String deepSeekApiKey; // 你的 DeepSeek API Key

    private final RestTemplate restTemplate;

    public DeepSeekService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getResponseFromAI(String userInput, List<ChatRequest.Message> history) {
        // ✅ 将 history 正确转换成符合 DeepSeek 格式的 messages
        List<Map<String, String>> messages = new ArrayList<>();

        for (ChatRequest.Message msg : history) {
            Map<String, String> map = new HashMap<>();
            // 👇 修正角色字段
            if ("user".equals(msg.getRole())) {
                map.put("role", "user");
            } else if ("ai".equals(msg.getRole())) {
                map.put("role", "assistant");
            } else {
                map.put("role", msg.getRole()); // 防止万一有system/tool等
            }
            map.put("content", msg.getContent());
            messages.add(map);
        }

        // ✅ 把当前输入也加进去
        Map<String, String> currentUserMessage = new HashMap<>();
        currentUserMessage.put("role", "user");
        currentUserMessage.put("content", userInput);
        messages.add(currentUserMessage);

        // ✅ 组装请求体
        Map<String, Object> body = new HashMap<>();
        body.put("model", "deepseek-chat");
        body.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(deepSeekApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<DeepSeekResponse> response = restTemplate.postForEntity(
                deepSeekApiUrl,
                entity,
                DeepSeekResponse.class
        );

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }


    // 用于接收 DeepSeek 的标准返回格式
    @Data
    public static class DeepSeekResponse {
        private List<Choice> choices;

        @Data
        public static class Choice {
            private Message message;
        }

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }
}
