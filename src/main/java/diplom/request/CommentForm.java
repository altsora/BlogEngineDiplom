package diplom.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentForm {
    @JsonProperty("post_id")
    private long postId;
    @JsonProperty("parent_id")
    private Object parentIdObj;
    @JsonProperty("text")
    private String text;
}
