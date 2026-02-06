package com.challenge.challege.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    private String content;
    private UserDTO author;
    private Long topicId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
