package sansan.auth.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sansan.auth.Entity.RequestEntity;

@Repository
public interface RequestEntityRepository extends JpaRepository<RequestEntity, String> {
    RequestEntity findByRequestIdAndUsernameAndStatus(String requestId, String username, String status);
}
