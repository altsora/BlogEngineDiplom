package diplom.service;

import diplom.enums.Rating;
import diplom.model.Post;
import diplom.repository.PostRepository;
import diplom.response.PostResponse;
import diplom.response.PublicPostsResponse;
import diplom.response.UserSimpleResponse;
import diplom.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static diplom.enums.ActivityStatus.ACTIVE;
import static diplom.enums.ModerationStatus.ACCEPTED;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final VoteService voteService;
    private final int maxAnnounceSize;

    //------------------------------------------------------------------------------------------------------------------

    public PublicPostsResponse getAllPosts(int offset, int limit, String mode) {
        List<Post> postListRep;
        int pageNumber = offset / limit;
        int count = postRepository.getTotalCountOfPosts(ACTIVE, ACCEPTED);
        Sort sort;
        Pageable pageable;
        switch (mode) {
            case "popular":
                sort = Sort.by(Sort.Direction.DESC, PostRepository.COUNT_COMMENTS);
                pageable = PageRequest.of(pageNumber, limit, sort);
                postListRep = postRepository.findPostsSortedByPopularDesc(ACTIVE, ACCEPTED, pageable);
                break;
            case "recent":
                sort = Sort.by(Sort.Direction.DESC, PostRepository.POST_TIME);
                pageable = PageRequest.of(pageNumber, limit, sort);
                postListRep = postRepository.findPostsSortedByDate(ACTIVE, ACCEPTED, pageable);
                break;
            case "early":
                sort = Sort.by(Sort.Direction.ASC, PostRepository.POST_TIME);
                pageable = PageRequest.of(pageNumber, limit, sort);
                postListRep = postRepository.findPostsSortedByDate(ACTIVE, ACCEPTED, pageable);
                break;
            case "best":
                sort = Sort.by(Sort.Direction.DESC, PostRepository.COUNT_LIKES);
                pageable = PageRequest.of(pageNumber, limit, sort);
                postListRep = postRepository.findPostsSortedByBest(ACTIVE, ACCEPTED, Rating.LIKE, pageable);
                break;
            default:
                postListRep = Collections.emptyList();
        }

        List<PostResponse> posts = getPostResponses(postListRep);
        return PublicPostsResponse.builder()
                .count(count)
                .posts(posts)
                .build();
    }

    private List<PostResponse> getPostResponses(List<Post> postListRep) {
        List<PostResponse> posts = new ArrayList<>();
        for (Post postRep : postListRep) {
            long postId = postRep.getId();
            long userId = postRep.getUser().getId();
            String userName = postRep.getUser().getName();
            long timestamp = TimeUtil.getTimestampFromLocalDateTime(postRep.getTime());

            PostResponse postResponse = PostResponse.builder()
                    .id(postId)
                    .timestamp(timestamp)
                    .title(postRep.getTitle())
                    .announce(getAnnounce(postRep.getText()))
                    .user(UserSimpleResponse.builder().id(userId).name(userName).build())
                    .likeCount(voteService.getCountLikesByPost(postRep))
                    .dislikeCount(voteService.getCountDislikeByPost(postRep))
                    .commentCount(commentService.getCountCommentsByPost(postRep))
                    .viewCount(postRep.getViewCount())
                    .build();
            posts.add(postResponse);
        }
        return posts;
    }

    private String getAnnounce(String text) {
        String announce = Jsoup.parse(text).text();
        return announce.length() > maxAnnounceSize ? announce.substring(0, maxAnnounceSize) : announce;
    }
}
