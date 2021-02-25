package diplom.service.validation;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.util.List;
import java.util.Objects;

@Component
public class ImageValidator implements Validator {
    private final List<String> formats = List.of("png", "jpg", "jpeg");

    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile file = (MultipartFile) target;
        String fileName = file.getOriginalFilename();
        String formatName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".") + 1);
        if (!formats.contains(formatName.toLowerCase())) {
            errors.reject("invalidFormat", "Допустимый формат изображения: png, jpg, jpeg");
        }
    }
}
