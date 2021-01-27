package diplom.service.validation;

import diplom.repository.UserRepository;
import diplom.request.RegisterForm;
import diplom.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
@RequiredArgsConstructor
public class RegisterValidator implements Validator {
    private final CaptchaService captchaService;
    private final UserRepository userRepository;
    @Value("${validation.user.minPassword}")
    private int minPassword;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterForm form = (RegisterForm) target;
        if (form.getPassword().length() < minPassword) {
            errors.reject("password");
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            errors.reject("email");
        }
        if (captchaService.isIncorrect(form.getCaptcha(), form.getCaptchaSecret())) {
            errors.reject("captcha");
        }

    }
}
