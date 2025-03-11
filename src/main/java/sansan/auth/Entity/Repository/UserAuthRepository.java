package sansan.auth.Entity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sansan.auth.Entity.UserAuth;


@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, String> {
}
