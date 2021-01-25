package diplom.service;

import com.github.cage.YCage;
import diplom.model.CaptchaCode;
import diplom.repository.CaptchaCodeRepository;
import diplom.response.CaptchaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

@Log4j2
@Service
@RequiredArgsConstructor
public class CaptchaService {
    @Value("${captcha.hours:0}")
    private int captchaLifeTimeHours;
    @Value("${captcha.minutes:0}")
    private int captchaLifeTimeMinutes;
    @Value("${captcha.length}")
    private int length;
    @Value("${captcha.width}")
    private int width;
    @Value("${captcha.height}")
    private int height;
    @Value("${captcha.imageEncoding}")
    private String imageEncoding;
    @Value("${captcha.formatName}")
    private String formatName;

    private final CaptchaCodeRepository captchaCodeRepository;

    //------------------------------------------------------------------------------------------------------------------

    public CaptchaResponse createCaptcha() {
        CaptchaCode captcha = generateCaptcha();
        String secret = captcha.getSecret();
        String image = getCaptchaImageCode(captcha.getCode());
        CaptchaResponse response = new CaptchaResponse();
        response.setSecret(secret);
        response.setImage(imageEncoding + image);
        return response;
    }

    public void clearOldCaptcha() {
        LocalDateTime time = LocalDateTime.now()
                .minusHours(captchaLifeTimeHours)
                .minusMinutes(captchaLifeTimeMinutes);
        captchaCodeRepository.removeAllByTimeBefore(time);
        log.info("Старые капчи успешно удалены.");
    }

    //------------------------------------------------------------------------------------------------------------------

    private String getCaptchaImageCode(String code) {
        BufferedImage image = new YCage().drawImage(code);
        if (image.getWidth() > width && image.getHeight() > height) {
            int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
            BufferedImage resizeImage = new BufferedImage(width, height, type);
            Graphics2D g = resizeImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
            image = resizeImage;
        }
        byte[] imageBytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, formatName, baos);
            imageBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private CaptchaCode generateCaptcha() {
        String code = generateCode();
        String secretCode = Base64.getEncoder().encodeToString(code.getBytes());
        CaptchaCode captcha = new CaptchaCode();
        captcha.setTime(LocalDateTime.now());
        captcha.setCode(code);
        captcha.setSecret(secretCode);
        return captchaCodeRepository.saveAndFlush(captcha);
    }

    private String generateCode() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }
}
