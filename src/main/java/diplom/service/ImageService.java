package diplom.service;

import com.github.cage.YCage;
import diplom.response.ErrorResponse;
import diplom.response.ResultResponse;
import diplom.service.validation.ImageValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${image.avatar.height}")
    private int avatarHeight;
    @Value("${image.avatar.width}")
    private int avatarWidth;
    @Value("${image.nameLength.folder}")
    private int folderNameLength;
    @Value("${image.nameLength.file}")
    private int imageNameLength;

    private final ImageValidator imageValidator;

    //------------------------------------------------------------------------------------------------------------------

    public String getCaptchaImageCode(String code, int width, int height, String formatName) {
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

    public ResponseEntity<?> uploadImage(MultipartFile file) {
//        DataBinder dataBinder = new DataBinder(file);
//        dataBinder.setValidator(imageValidator);
//        dataBinder.validate();
//        BindingResult bindingResult = dataBinder.getBindingResult();
//        ErrorResponse errors = new ErrorResponse();
//        if (!bindingResult.hasErrors()) {
//            return getFileName(file, errors);
//        }
//        log.warn("Файл не прошёл валидацию.");
//        bindingResult.getAllErrors().forEach(e -> errors.setImage(e.getDefaultMessage()));
//        return ResponseEntity.badRequest().body(ResultResponse.builder().result(false).errors(errors).build());
        return null;
    }

    private ResponseEntity<?> getFileName(MultipartFile file, ErrorResponse errors) {
        log.info("Загружаем файл.");
        if (file.isEmpty()) {
            errors.setImage("Файл пустой.");
            return ResponseEntity.badRequest().body(ResultResponse.builder().result(false).errors(errors).build());
        }
        StringBuilder fullPath = new StringBuilder("src/main/resources/upload/");


        return null;
    }
}
