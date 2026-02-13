package com.go_exchange_easier.backend.chat.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateMessageRequest(

        @NotNull(message = "Room id can not be null.")
        @Schema(example = "185ab299-20ae-4e77-b8d5-77eca47d12df")
        UUID roomId,

        @NotBlank(message = "Text content can not be null, empty or whitespace only.")
        @Schema(example = "Hello. How are you?")
        String textContent

) { }
