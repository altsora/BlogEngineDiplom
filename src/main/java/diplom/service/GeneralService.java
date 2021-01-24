package diplom.service;

import diplom.model.Post;
import diplom.model.User;
import diplom.response.StatisticResponse;
import diplom.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GeneralService {
    private final VoteService voteService;
    private final AuthService authService;
    private final PostService postService;
    private final GlobalSettingService globalSettingService;

    //------------------------------------------------------------------------------------------------------------------

    @Transactional
    public StatisticResponse getMyStatistics() {
        User user = authService.getCurrentUser();
        int likesCount = voteService.getLikesCountByUser(user);
        int dislikesCount = voteService.getCountDislikesByUser(user);
        int postsCount = user.getPosts().size();
        int viewCount = postService.getCountViewByUserId(user.getId());
        LocalDateTime postTime = user.getPosts().stream()
                .map(Post::getTime)
                .sorted()
                .findFirst().orElse(null);
        long timestamp = TimeUtil.getTimestamp(postTime);

        return StatisticResponse.builder()
                .dislikesCount(dislikesCount)
                .likesCount(likesCount)
                .postsCount(postsCount)
                .viewsCount(viewCount)
                .firstPublication(timestamp)
                .build();
    }

    @Transactional
    public ResponseEntity<StatisticResponse> getAllStatistics() {
        if (authService.currentUserIsModerator() || globalSettingService.publicStatisticsEnabled()) {
            int likesCount = voteService.getLikesCount();
            int dislikesCount = voteService.getDislikesCount();
            int postsCount = postService.countAvailablePosts();
            int viewCount = postService.getViewCount();
            LocalDateTime postTime = postService.getTimeOfFirstPost();
            long timestamp = TimeUtil.getTimestamp(postTime);

            StatisticResponse response = StatisticResponse.builder()
                    .dislikesCount(dislikesCount)
                    .likesCount(likesCount)
                    .postsCount(postsCount)
                    .viewsCount(viewCount)
                    .firstPublication(timestamp)
                    .build();

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //------------------------------------------------------------------------------------------------------------------
}
