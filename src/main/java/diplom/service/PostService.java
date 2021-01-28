package diplom.service;

import diplom.model.User;
import diplom.model.enums.ActivityStatus;
import diplom.model.enums.ModerationStatus;
import diplom.model.enums.Rating;
import diplom.model.Post;
import diplom.model.Tag;
import diplom.repository.PostRepository;
import diplom.request.PostForm;
import diplom.response.*;
import diplom.service.validation.PostValidator;
import diplom.utils.TimeCountWrapper;
import diplom.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static diplom.model.enums.ActivityStatus.ACTIVE;
import static diplom.model.enums.ActivityStatus.INACTIVE;
import static diplom.model.enums.ModerationStatus.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final VoteService voteService;
    private final AuthService authService;
    private final PostValidator postValidator;
    private final GlobalSettingService globalSettingService;

    @Value("${post.maxAnnounceSize}")
    private int maxAnnounceSize;

    //------------------------------------------------------------------------------------------------------------------

    public PublicPostsResponse getAllPosts(int offset, int limit, String mode) {
        List<Post> postListRep;
        int pageNumber = offset / limit;
        int count = postRepository.countPosts(ACTIVE, ACCEPTED);
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
        return PublicPostsResponse.builder().count(count).posts(posts).build();
    }

    public PublicPostsResponse searchPostsByQuery(int offset, int limit, String query) {
        Sort sort = Sort.by(Sort.Direction.DESC, PostRepository.POST_TIME);
        PageRequest pageable = PageRequest.of(offset / limit, limit, sort);
        List<Post> postListRep = query.isEmpty() ?
                postRepository.findPostsSortedByDate(ACTIVE, ACCEPTED, pageable) :
                postRepository.findPostsByQuery(ACTIVE, ACCEPTED, query, pageable);

        List<PostResponse> posts = getPostResponses(postListRep);
        int count = query.isEmpty() ?
                postRepository.countPosts(ACTIVE, ACCEPTED) :
                postRepository.getCountPostsByQuery(ACTIVE, ACCEPTED, query);

        return PublicPostsResponse.builder().count(count).posts(posts).build();
    }

    public CalendarResponse getCalendar(Integer year) {
        if (year == null) year = LocalDateTime.now().getYear();
        List<Integer> years = postRepository.findYearsOfPublication(ACTIVE, ACCEPTED);
        int currentYear = LocalDateTime.now().getYear();
        if (!years.contains(currentYear)) years.add(0, currentYear);
        List<TimeCountWrapper> timeAndCountPosts = postRepository.getTimeAndCountPosts(ACTIVE, ACCEPTED, year);
        Map<String, Long> posts = timeAndCountPosts.stream()
                .collect(Collectors.toMap(TimeCountWrapper::getTime, TimeCountWrapper::getCount));
        return CalendarResponse.builder().years(years).posts(posts).build();
    }

    public PublicPostsResponse searchPostsByDate(int offset, int limit, String date) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        List<Post> postListRep = postRepository.findPostsByDate(ACTIVE, ACCEPTED, date, pageable);
        List<PostResponse> posts = getPostResponses(postListRep);
        int count = postRepository.countPostsByDate(ACTIVE, ACCEPTED, date);
        return PublicPostsResponse.builder().count(count).posts(posts).build();
    }

    public PublicPostsResponse searchPostsByTag(int offset, int limit, String tag) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        List<Post> postListRep = postRepository.findPostsByTag(ACTIVE, ACCEPTED, tag, pageable);
        List<PostResponse> posts = getPostResponses(postListRep);
        int count = postRepository.countPostsByTag(ACTIVE, ACCEPTED, tag);
        return PublicPostsResponse.builder().count(count).posts(posts).build();
    }

    public ResponseEntity<CurrentPostResponse> getPostById(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) return ResponseEntity.notFound().build();
        Post postRep = optionalPost.get();
        //TODO проверка аутентификации, увеличение числа просмотров
        long userId = postRep.getUser().getId();
        String userName = postRep.getUser().getName();
        long timestamp = TimeUtil.getTimestamp(postRep.getTime());
        List<String> tags = postRep.getTags().stream().map(Tag::getName).collect(Collectors.toList());

        CurrentPostResponse post = CurrentPostResponse.builder()
                .id(postRep.getId())
                .timestamp(timestamp)
                .active(postRep.getActivityStatus() == ACTIVE)
                .user(UserSimpleResponse.builder().id(userId).name(userName).build())
                .title(postRep.getTitle())
                .text(postRep.getText())
                .likeCount(voteService.getCountLikesByPost(postRep))
                .dislikeCount(voteService.getCountDislikeByPost(postRep))
                .viewCount(postRep.getViewCount())
                .comments(commentService.getCommentResponseByPost(postRep))
                .tags(tags)
                .build();
        return ResponseEntity.ok(post);
    }

    public PublicPostsResponse getMyPosts(int offset, int limit, String status) {
        User user = authService.getCurrentUser();
        int count;
        List<Post> postListRep;
        Pageable pageable = PageRequest.of(offset / limit, limit);
        switch (status) {
            case "inactive":
                postListRep = postRepository.findPostsByActivityStatusAndUser(INACTIVE, user, pageable);
                count = postRepository.countPostsByActivityStatusAndUser(INACTIVE, user);
                break;
            case "pending":
                postListRep = postRepository.findByActivityStatusAndModerationStatusAndUser(ACTIVE, NEW, user, pageable);
                count = postRepository.countByActivityStatusAndModerationStatusAndUser(ACTIVE, NEW, user);
                break;
            case "declined":
                postListRep = postRepository.findByActivityStatusAndModerationStatusAndUser(ACTIVE, DECLINED, user, pageable);
                count = postRepository.countByActivityStatusAndModerationStatusAndUser(ACTIVE, DECLINED, user);
                break;
            case "published":
                postListRep = postRepository.findByActivityStatusAndModerationStatusAndUser(ACTIVE, ACCEPTED, user, pageable);
                count = postRepository.countByActivityStatusAndModerationStatusAndUser(ACTIVE, ACCEPTED, user);
                break;
            default:
                postListRep = Collections.emptyList();
                count = 0;
        }

        List<PostResponse> posts = getPostResponses(postListRep);
        return PublicPostsResponse.builder().posts(posts).count(count).build();
    }

    public PublicPostsResponse postsToPublish(int offset, int limit, String status) {
        User moderator = authService.getCurrentUser();
        int count;
        List<Post> postListRep;
        Pageable pageable = PageRequest.of(offset / limit, limit);
        switch (status) {
            case "declined":
                postListRep = postRepository.findPostsByModerator(ACTIVE, DECLINED, moderator, pageable);
                count = postRepository.countPostsByModerator(ACTIVE, DECLINED, moderator);
                break;
            case "accepted":
                postListRep = postRepository.findPostsByModerator(ACTIVE, ACCEPTED, moderator, pageable);
                count = postRepository.countPostsByModerator(ACTIVE, ACCEPTED, moderator);
                break;
            case "new":
                postListRep = postRepository.findByActivityStatusAndModerationStatus(ACTIVE, NEW, pageable);
                count = postRepository.countByActivityStatusAndModerationStatus(ACTIVE, NEW);
                break;
            default:
                postListRep = Collections.emptyList();
                count = 0;
        }
        List<PostResponse> posts = getPostResponses(postListRep);
        return PublicPostsResponse.builder().posts(posts).count(count).build();
    }

    public ResultResponse addNewPost(PostForm form) {
        System.err.println("form = " + form);
        DataBinder dataBinder = new DataBinder(form);
        dataBinder.setValidator(postValidator);
        dataBinder.validate();
        BindingResult bindingResult = dataBinder.getBindingResult();

        if (!bindingResult.hasErrors()) {
            addPost(form);
            return ResultResponse.builder().result(true).build();
        }
        log.warn("Пост не добавлен: есть ошибки.");
        ErrorResponse errors = new ErrorResponse();
        bindingResult.getAllErrors().forEach(e -> {
            switch (Objects.requireNonNull(e.getCode())) {
                case "emptyTitle":
                    errors.setTitle("Заголовок не должен быть пустым.");
                    break;
                case "minTitle":
                    errors.setTitle("Минимальное количество символов в заголовке - 3.");
                    break;
                case "emptyText":
                    errors.setText("Пост не должен быть пустым.");
                    break;
                case "minText":
                    errors.setText("Минимальное количество символов в публикации - 50");
                    break;
            }
        });

        return ResultResponse.builder()
                .result(false)
                .errors(errors)
                .build();
    }

    //------------------------------------------------------------------------------------------------------------------

    public int countAvailablePosts() {
        return countPosts(ACTIVE, ACCEPTED);
    }

    public int countPosts(ActivityStatus activityStatus, ModerationStatus moderationStatus) {
        return postRepository.countPosts(activityStatus, moderationStatus);
    }

    public int countPostsByTag(String tag) {
        return postRepository.countPostsByTag(ACTIVE, ACCEPTED, tag);
    }

    public int getCountViewByUserId(long userId) {
        return postRepository.getTotalViewCountByUser(userId);
    }

    public int getViewCount() {
        return postRepository.getTotalViewCount();
    }

    public LocalDateTime getTimeOfFirstPost() {
        return postRepository.getTimeOfFirstPost();
    }

    public Post getOne(long postId) {
        return postRepository.getOne(postId);
    }

    public Optional<Post> findById(long postId) {
        return postRepository.findById(postId);
    }

    public Post saveAndFlush(Post post) {
        return postRepository.saveAndFlush(post);
    }
    //------------------------------------------------------------------------------------------------------------------

    private List<PostResponse> getPostResponses(List<Post> postListRep) {
        List<PostResponse> posts = new ArrayList<>();
        for (Post postRep : postListRep) {
            long postId = postRep.getId();
            long userId = postRep.getUser().getId();
            String userName = postRep.getUser().getName();
            long timestamp = TimeUtil.getTimestamp(postRep.getTime());

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
        return announce.length() > maxAnnounceSize ?
                announce.substring(0, maxAnnounceSize) + "..." :
                announce;
    }

    private void addPost(PostForm form) {
        User user = authService.getCurrentUser();
        String text = form.getText();
        String title = form.getTitle();
        ActivityStatus activity = form.getActive() == 1 ? ACTIVE : INACTIVE;
        long timestamp = form.getTimestamp();
        List<String> tags = form.getTags();

        Post post = new Post();
        post.setUser(user);
        post.setText(text);
        post.setTitle(title);
        post.setTime(TimeUtil.getLocalDateTimeCheck(timestamp));
        post.setActivityStatus(activity);
        if (!globalSettingService.preModerationEnabled()) {
            post.setModerationStatus(ACCEPTED);
        }
        post.addTags(tags);
        log.info("Новый пост добавлен.");
        postRepository.saveAndFlush(post);
    }

}
