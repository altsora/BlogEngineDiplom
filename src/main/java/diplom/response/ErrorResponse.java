package diplom.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String captcha;
    private String email;
    private String code;
    private String image;
    private String name;
    private String password;
    private String photo;
    private String text;
    private String title;
}
