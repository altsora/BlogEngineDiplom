package diplom.controller;

import diplom.request.LoginRequest;
import diplom.response.ResultResponse;
import diplom.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

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
    public ResultResponse logout(Principal principal) {
        return authService.logout();
    }

}
