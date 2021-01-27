package diplom.controller;

import diplom.model.enums.Rating;
import diplom.request.RatingForm;
import diplom.response.CurrentPostResponse;
import diplom.response.PublicPostsResponse;
import diplom.response.ResultResponse;
import diplom.service.PostService;
import diplom.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    private final VoteService voteService;

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping
    public PublicPostsResponse getAllPosts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "mode", defaultValue = "recent") String mode
    ) {
        return postService.getAllPosts(offset, limit, mode);
    }

    @GetMapping("/search")
    public PublicPostsResponse searchPostsByQuery(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "query") String query
    ) {
        return postService.searchPostsByQuery(offset, limit, query);
    }

    @GetMapping("/byDate")
    public PublicPostsResponse searchPostsByDate(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "date") String date
    ) {
        return postService.searchPostsByDate(offset, limit, date);
    }

    @GetMapping("/byTag")
    public PublicPostsResponse searchPostsByTag(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "tag") String tag
    ) {
        return postService.searchPostsByTag(offset, limit, tag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrentPostResponse> getPostById(@PathVariable(value = "id") long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('user:write')")
    public PublicPostsResponse getMyPosts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status") String status
    ) {
        return postService.getMyPosts(offset, limit, status);
    }

    @PostMapping("/like")
    @PreAuthorize("hasAuthority('user:write')")
    public ResultResponse putLike(@RequestBody RatingForm form) {
        return voteService.put(Rating.LIKE, form.getPostId());
    }

    @PostMapping("/dislike")
    @PreAuthorize("hasAuthority('user:write')")
    public ResultResponse putDislike(@RequestBody RatingForm form) {
        return voteService.put(Rating.DISLIKE, form.getPostId());
    }

    @GetMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public PublicPostsResponse postsToPublish(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status", defaultValue = "new") String status
    ) {
        return postService.postsToPublish(offset, limit, status);
    }

}
