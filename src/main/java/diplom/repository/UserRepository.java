package diplom.repository;

import diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
