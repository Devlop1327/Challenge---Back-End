package com.challenge.challege.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostDTO {
    @NotBlank
    private String content;
}
