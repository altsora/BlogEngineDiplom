package diplom.controller;

import diplom.request.CommentForm;
import diplom.request.ModerationForm;
import diplom.request.SettingsForm;
import diplom.response.*;
import diplom.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeneralController {
    private final Blog blog;
    private final GlobalSettingService globalSettingService;
    private final PostService postService;
    private final TagService tagService;
    private final GeneralService generalService;
    private final CommentService commentService;

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("init")
    public Blog init() {
        return blog;
    }

    @GetMapping("/settings")
    public Map<String, Boolean> getSettings() {
        return globalSettingService.getSettings();
    }

    @PutMapping("/settings")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<String> setSettings(@RequestBody SettingsForm settingsForm) {
        return globalSettingService.setSettings(settingsForm);
    }

    @GetMapping("calendar")
    public CalendarResponse getCalendar(@RequestParam(value = "year", required = false) Integer year) {
        return postService.getCalendar(year);
    }

    @GetMapping(value = "/tag")
    public ResultResponse getTagList(@RequestParam(value = "query", required = false) String query) {
        return tagService.getTagList(query);
    }

    @GetMapping("/statistics/my")
    @PreAuthorize("hasAuthority('user:write')")
    public StatisticResponse getMyStatistics() {
        return generalService.getMyStatistics();
    }

    @GetMapping("/statistics/all")
    public ResponseEntity<StatisticResponse> getAllStatistics() {
        return generalService.getAllStatistics();
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AbstractResponse> addComment(@RequestBody CommentForm form) {
        return commentService.addComment(form);
    }

    @PostMapping("/moderation")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResultResponse moderation(@RequestBody ModerationForm form) {
        return generalService.moderation(form);
    }
}
