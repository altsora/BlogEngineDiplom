package diplom.response;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class UserSimpleResponse {
    private final long id;
    private final String name;
}
