package com.go_exchange_easier.backend.core.domain.report.user;

import com.go_exchange_easier.backend.core.domain.report.Report;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "user_reports", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "report_id")
public class UserReport extends Report {

    @Column(name = "context")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> context;

    @Column(name = "reported_user_id")
    private int reportedUserId;

}