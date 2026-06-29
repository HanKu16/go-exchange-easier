package com.go_exchange_easier.backend.core.domain.report.chat;

import com.go_exchange_easier.backend.core.domain.report.Report;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "chat_reports", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "report_id")
public class ChatReport extends Report {

    @Column(name = "context")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> context;

    @Column(name = "reported_user_id")
    private int reportedUserId;

    @Column(name = "room_id")
    private UUID roomId;

}
