package diplom.controller;

import diplom.response.CurrentPostResponse;
import diplom.response.PublicPostsResponse;
import diplom.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping
//    @PreAuthorize("hasAuthority('user:write')")
    public PublicPostsResponse getAllPosts(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "mode", defaultValue = "recent") String mode
    ) {
        return postService.getAllPosts(offset, limit, mode);
    }

    @GetMapping("/search")
//    @PreAuthorize("hasAuthority('user:moderate')")
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

}
