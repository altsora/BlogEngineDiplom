package diplom.service.schedule;

import diplom.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleTasks {
    private final CaptchaService captchaService;

    @Scheduled(fixedDelay = 1000 * 3600 * 3) // 3h
    public void clearOldCaptcha() {
        captchaService.clearOldCaptcha();
    }

}
