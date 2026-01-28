package com.go_exchange_easier.backend.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body for successful assigning country to user")
public record AssignCountryOfOriginResponse(

        Integer userId,
        CountryDto country

) {

    public record CountryDto(

            Short id,
            String name

    ) { }

}
