package diplom.service;

import diplom.model.User;
import diplom.repository.UserRepository;
import diplom.request.LoginRequest;
import diplom.response.ResultResponse;
import diplom.response.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public boolean authorized(Principal principal) {
        return principal != null;
    }

    public ResultResponse check(Principal principal) {
        return authorized(principal) ?
                getResultResponse(principal.getName()) :
                ResultResponse.builder().result(false).build();
    }

    public ResultResponse login(LoginRequest loginRequest) {
        Authentication auth;
        try {
            auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (RuntimeException ex) {
            return ResultResponse.builder().result(false).build();
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        org.springframework.security.core.userdetails.User authorizedUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return getResultResponse(authorizedUser.getUsername());
    }

    public ResultResponse logout() {
        return ResultResponse.builder().result(true).build();
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByEmail(username).orElse(null);
    }

    public boolean currentUserIsModerator() {
        User user = getCurrentUser();
        return user != null && user.isModerator();
    }

    public boolean isAuthorized(Principal principal) {
        return principal != null;
    }

    public ResultResponse getResultResponse(String email) {
        diplom.model.User currentUser = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .photo(currentUser.getPhoto())
                .email(currentUser.getEmail())
                .moderation(currentUser.isModerator())
                .moderationCount(0) //TODO: нужен подсчёт
                .build();

        return ResultResponse.builder()
                .result(true)
                .user(userLoginResponse)
                .build();
    }


}
