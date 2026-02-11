package com.go_exchange_easier.backend.chat.message;

import com.go_exchange_easier.backend.chat.message.dto.MessageDetails;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface MessageService {

    SimplePage<MessageDetails> getPage(UUID roomId, Pageable pageable);

}
