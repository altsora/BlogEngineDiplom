package diplom.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ResultResponse {
    private boolean result;
    private List<TagResponse> tags;
    private UserLoginResponse user;
}
