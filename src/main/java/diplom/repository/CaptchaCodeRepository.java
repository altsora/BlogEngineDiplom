package diplom.repository;

import diplom.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Long> {

    @Transactional
    void removeAllByTimeBefore(LocalDateTime time);

    Optional<CaptchaCode> getBySecret(String secret);
}