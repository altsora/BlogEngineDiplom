package diplom.controller;

import diplom.request.LoginRequest;
import diplom.request.RegisterForm;
import diplom.response.CaptchaResponse;
import diplom.response.ResultResponse;
import diplom.service.AuthService;
import diplom.service.CaptchaService;
import diplom.service.GlobalSettingService;
import diplom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final CaptchaService captchaService;
    private final UserService userService;

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/check")
    public ResultResponse check(Principal principal) {
        return authService.check(principal);
    }

    @PostMapping("/login")
    public ResultResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('user:write')")
    public ResultResponse logout() {
        return authService.logout();
    }

    @GetMapping("/captcha")
    public CaptchaResponse createCaptcha() {
        return captchaService.createCaptcha();
    }

    @PostMapping("/register")
    public ResponseEntity<ResultResponse> registration(@RequestBody RegisterForm form) {
        return userService.registration(form);
    }

}
