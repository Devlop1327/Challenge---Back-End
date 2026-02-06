package com.challenge.challege.controller;

import com.challenge.challege.dto.CreateTopicDTO;
import com.challenge.challege.dto.TopicDTO;
import com.challenge.challege.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<Page<TopicDTO>> list(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Page<TopicDTO> topics = topicService.listTopics(PageRequest.of(page, size));
        return ResponseEntity.ok(topics);
    }

    @PostMapping
    public ResponseEntity<TopicDTO> create(@Valid @RequestBody CreateTopicDTO dto, Authentication authentication) {
        String username = authentication.getName();
        TopicDTO created = topicService.createTopic(dto, username);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> get(@PathVariable Long id) {
        TopicDTO topic = topicService.getTopic(id);
        return ResponseEntity.ok(topic);
    }
}
