package com.vis.accountingvis.controller;

import com.vis.accountingvis.dto.ChatRequest;
import com.vis.accountingvis.service.DeepSeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final DeepSeekService deepSeekService;

    @Autowired
    public ChatController(DeepSeekService deepSeekService) {
        this.deepSeekService = deepSeekService;
    }

    @PostMapping("/message")
    public String sendMessage(@RequestBody ChatRequest chatRequest) {
        return deepSeekService.getResponseFromAI(chatRequest.getMessage(), chatRequest.getHistory());
    }
}
