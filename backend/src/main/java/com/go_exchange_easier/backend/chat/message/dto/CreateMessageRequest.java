package com.go_exchange_easier.backend.chat.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMessageRequest(

        @NotBlank(message = "Text content can not be null, empty or whitespace only.")
        @Size(max = 1000, message = "Max message size is 1000 characters.")
        @Schema(example = "Hello. How are you?")
        String textContent

) { }
