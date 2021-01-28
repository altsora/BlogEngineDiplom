package diplom.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModerationForm {
    @JsonProperty("post_id") private long postId;
    @JsonProperty("decision") private String decision;
}
