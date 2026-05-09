package central.auth_service.repository;

import central.auth_service.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession,Long> {
}
