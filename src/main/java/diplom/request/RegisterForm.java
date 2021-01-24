package diplom.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterForm {
    @JsonProperty("e_mail") private String email;
    @JsonProperty("name") private String name;
    @JsonProperty("password") private String password;
    @JsonProperty("captcha") private String captcha;
    @JsonProperty("captcha_secret") private String captchaSecret;
}
