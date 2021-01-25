package diplom.service.validation;

import diplom.repository.UserRepository;
import diplom.request.RegisterForm;
import diplom.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
@RequiredArgsConstructor
public class RegisterValidator implements Validator {
    private final CaptchaService captchaService;
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterForm form = (RegisterForm) target;
        if (form.getPassword().length() < 6) {
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
