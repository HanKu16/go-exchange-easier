package com.go_exchange_easier.backend.chat.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateMessageRequest(

        @NotNull(message = "Room id can not be null.")
        @Schema(example = "185ab299-20ae-4e77-b8d5-77eca47d12df")
        UUID roomId,

        @NotBlank(message = "Text content can not be null, empty or whitespace only.")
        @Size(max = 1000, message = "Max message size is 1000 characters.")
        @Schema(example = "Hello. How are you?")
        String textContent

) { }
