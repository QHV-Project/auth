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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_auth")
public class UserAuth {
    @Id
    @JsonProperty("id")
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    @JsonProperty("token")
    @Column(name = "token")
    private String token;

    @JsonProperty("status")
    @Column(name = "status")
    private String status;

    @JsonProperty("create_time")
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
