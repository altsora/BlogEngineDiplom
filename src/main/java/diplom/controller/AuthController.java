package diplom.controller;

import diplom.request.LoginRequest;
import diplom.request.RegisterForm;
import diplom.response.ResultResponse;
import diplom.service.AuthService;
import diplom.service.GlobalSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final GlobalSettingService globalSettingService;

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
    public ResultResponse logout(Principal principal) {
        return authService.logout();
    }

    @PostMapping("/register")
    public ResponseEntity<ResultResponse> registration(@RequestBody RegisterForm form) {
        System.err.println("Мы внутри регистрации!");
        System.err.println(form);
        if (!globalSettingService.multiuserModeEnabled()) {
            return ResponseEntity.notFound().build();
        }
        //TODO
        return null;
    }

}
