package com.challenge.challege.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTopicDTO {
    @NotBlank
    private String title;

    private String description;
}
