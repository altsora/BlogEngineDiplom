package diplom.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class PublicPostsResponse {
    private final int count;
    @Singular
    private final List<PostResponse> posts;
}
