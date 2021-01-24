package diplom.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettingsForm {
    @JsonProperty("MULTIUSER_MODE") private boolean multiUserModeValue;
    @JsonProperty("POST_PREMODERATION") private boolean postPreModerationValue;
    @JsonProperty("STATISTICS_IS_PUBLIC") private boolean statisticsIsPublicValue;
}
