package diplom.repository;

import diplom.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Long> {

    @Transactional
    void removeAllByTimeBefore(LocalDateTime time);
}