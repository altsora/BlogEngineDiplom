package diplom.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostForm {
    @JsonProperty("timestamp") private long timestamp;
    @JsonProperty("active") private int active;
    @JsonProperty("title") private String title;
    @JsonProperty("tags") private List<String> tags;
    @JsonProperty("text") private String text;
}
