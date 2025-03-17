package sansan.auth.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "request_entity")
public class RequestEntity {
    @Id
    @JsonProperty("request_id")
    @Column(name = "request_id", nullable = false)
    private String requestId;
    @JsonProperty("user_name")
    @Column(name = "user_name")
    private String username;

    @JsonProperty("request_type")
    @Column(name = "request_type")
    private String requestType;

    @JsonProperty("status")
    @Column(name = "status")
    private String status;

    @JsonProperty("create_time")
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @JsonProperty("update_time")
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
