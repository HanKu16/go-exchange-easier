package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.user.User;
import java.io.Serializable;

public record UserSummary(

        Integer id,
        String nick

)  implements Serializable { }
