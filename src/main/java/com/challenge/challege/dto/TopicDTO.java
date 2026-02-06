package com.challenge.challege.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TopicDTO {
    private Long id;
    private String title;
    private String description;
    private UserDTO author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
