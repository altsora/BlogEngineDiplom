package diplom.response;

import lombok.Data;

@Data
public class CaptchaResponse {
    private String image;
    private String secret;
}
