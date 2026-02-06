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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> get(@PathVariable Long id) {
        TopicDTO topic = topicService.getTopic(id);
        return ResponseEntity.ok(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> update(@PathVariable Long id, @Valid @RequestBody CreateTopicDTO dto, Authentication authentication) {
        String username = authentication.getName();
        TopicDTO updated = topicService.updateTopic(id, dto, username);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        topicService.deleteTopic(id, username);
        return ResponseEntity.noContent().build();
    }
}
