package diplom.service;

import diplom.model.User;
import diplom.repository.UserRepository;
import diplom.request.RegisterForm;
import diplom.response.ErrorResponse;
import diplom.response.ResultResponse;
import diplom.service.validation.RegisterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GlobalSettingService globalSettingService;
    private final RegisterValidator registerValidator;
    private final PasswordEncoder passwordEncoder;

    //------------------------------------------------------------------------------------------------------------------

    public ResponseEntity<ResultResponse> registration(RegisterForm form) {
        if (!globalSettingService.multiuserModeEnabled()) {
            return ResponseEntity.notFound().build();
        }

        DataBinder dataBinder = new DataBinder(form);
        dataBinder.addValidators(registerValidator);
        dataBinder.validate();
        BindingResult bindingResult = dataBinder.getBindingResult();

        if (!bindingResult.hasErrors()) {
            User user = new User();
            user.setRegTime(LocalDateTime.now());
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            log.info("Успешная регистрация!");
            userRepository.saveAndFlush(user);
            return ResponseEntity.ok(ResultResponse.builder().result(true).build());
        }
        log.info("Не удалось выполнить регистрацию: введены некорректные данные");
        ErrorResponse errorResponse = new ErrorResponse();
        bindingResult.getAllErrors().forEach(error -> {
            String code = error.getCode();
            switch (code) {
                case "password":
                    errorResponse.setPassword("Пароль короче 6-ти символов");
                    break;
                case "name":
                    errorResponse.setName("Имя указано неверно");
                    break;
                case "captcha":
                    errorResponse.setCaptcha("Код с картинки введён неверно");
                    break;
                case "email":
                    errorResponse.setEmail("Этот e-mail уже зарегистрирован");
                    break;
            }
        });

        ResultResponse resultResponse = ResultResponse.builder()
                .result(false)
                .errors(errorResponse)
                .build();
        return ResponseEntity.ok(resultResponse);
    }

    //------------------------------------------------------------------------------------------------------------------

}
