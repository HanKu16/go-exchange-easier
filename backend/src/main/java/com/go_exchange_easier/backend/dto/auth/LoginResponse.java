package com.go_exchange_easier.backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body for a successful user login")
public record LoginResponse(

        @Schema(description = "The unique identifier of the authenticated user",
                example = "23")
        Integer userId,

        @Schema(description = "The JWT (JSON Web Token) for authentication",
                example = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVI" +
                        "iXSwidXNlcklkIjoxMDEsInVzZXJuYW1lIjoicGlvdHJhc0R6a" +
                        "WtqamoiLCJzdWIiOiIxMDEiLCJpYXQiOjE3NTUwNzE0MDEsImV" +
                        "4cCI6MTc1NTA3MzIwMX0.oxoOsvO2sAWa1ir2CkSPCNmFkWvoO" +
                        "MkJEbj9KUdKrzA")
        String accessToken,

        @Schema(description = "The type of the token", example = "Bearer")
        String tokenType

) { }
