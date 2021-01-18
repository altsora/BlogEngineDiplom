package diplom.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserWithPhotoResponse {
    private final long id;
    private final String name;
    private final String photo;
}
