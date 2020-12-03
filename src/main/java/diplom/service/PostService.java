package diplom.service;

import diplom.enums.ActivityStatus;
import diplom.enums.ModerationStatus;
import diplom.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //------------------------------------------------------------------------------------------------------------------

    public int getTotalCountOfPosts(ActivityStatus activityStatus, ModerationStatus moderationStatus) {
        return postRepository.getTotalCountOfPosts(activityStatus, moderationStatus);
    }
}
