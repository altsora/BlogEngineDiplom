package diplom.controller;

import diplom.response.PublicPostsResponse;
import diplom.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping
    public PublicPostsResponse getAllPosts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit") int limit,
            @RequestParam(value = "mode") String mode
    ) {
        return postService.getAllPosts(offset, limit, mode);
    }

    @GetMapping("/search")
    public PublicPostsResponse searchPostsByQuery(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit") int limit,
            @RequestParam(value = "query") String query
    ) {
        return postService.searchPostsByQuery(offset, limit, query);
    }

    @GetMapping("/byDate")
    public PublicPostsResponse searchPostsByDate(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit") int limit,
            @RequestParam(value = "date") String date
    ) {
        return postService.searchPostsByDate(offset, limit, date);
    }

    @GetMapping("/byTag")
    public PublicPostsResponse searchPostsByTag(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit") int limit,
            @RequestParam(value = "tag") String tag
    ) {
        return postService.searchPostsByTag(offset, limit, tag);
    }

}
