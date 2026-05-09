package central.auth_service.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String deviceId;

    @Column(length = 1000)
    private String refreshToken;

    private LocalDateTime loginTime;

    private LocalDateTime expiryTime;

    private boolean active;
}
