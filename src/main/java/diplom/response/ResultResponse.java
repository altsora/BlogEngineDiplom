package diplom.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ResultResponse extends AbstractResponse {
    private boolean result;
    private List<TagResponse> tags;
    private UserLoginResponse user;
    private ErrorResponse errors;
    private Long id;
}
