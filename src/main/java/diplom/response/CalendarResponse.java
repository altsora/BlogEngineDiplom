package diplom.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@RequiredArgsConstructor
public class CalendarResponse {
    @Singular
    private final List<Integer> years;
    @Singular
    private final Map<String, Long> posts;
}
