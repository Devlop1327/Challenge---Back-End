package com.challenge.challege.controller;

import com.challenge.challege.dto.CreatePostDTO;
import com.challenge.challege.dto.PostDTO;
import com.challenge.challege.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/topics/{topicId}/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostDTO>> list(@PathVariable Long topicId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Page<PostDTO> posts = postService.listPostsByTopic(topicId, PageRequest.of(page, size));
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@PathVariable Long topicId, @Valid @RequestBody CreatePostDTO dto, Authentication authentication) {
        // Defensive check: if authentication is null, return 401 instead of throwing NPE
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = authentication.getName();
        PostDTO created = postService.addPost(topicId, dto, username);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> update(@PathVariable Long topicId, @PathVariable Long postId, @Valid @RequestBody CreatePostDTO dto, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = authentication.getName();
        PostDTO updated = postService.updatePost(postId, dto, username);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long topicId, @PathVariable Long postId, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = authentication.getName();
        postService.deletePost(postId, username);
        return ResponseEntity.noContent().build();
    }
}
