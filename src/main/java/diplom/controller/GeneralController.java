package diplom.controller;

import diplom.response.Blog;
import diplom.response.CalendarResponse;
import diplom.response.ResultResponse;
import diplom.service.GlobalSettingService;
import diplom.service.PostService;
import diplom.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeneralController {
    private final Blog blog;
    private final GlobalSettingService globalSettingService;
    private final PostService postService;
    private final TagService tagService;

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("init")
    public Blog init() {
        return blog;
    }

    @GetMapping("/settings")
    public Map<String, Boolean> getSettings() {
        return globalSettingService.getSettings();
    }

    @GetMapping("calendar")
    public CalendarResponse getCalendar(@RequestParam(value = "year", required = false) Integer year) {
        return postService.getCalendar(year);
    }

    @GetMapping(value = "/tag")
    public ResultResponse getTagList(@RequestParam(value = "query", required = false) String query) {
        return tagService.getTagList(query);
    }
}
