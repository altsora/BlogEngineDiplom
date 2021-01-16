package diplom.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResultResponse {
    private boolean result;
    private List<TagResponse> tags;
}
