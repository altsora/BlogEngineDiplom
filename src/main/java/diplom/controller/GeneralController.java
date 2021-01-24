package diplom.controller;

import diplom.request.SettingsForm;
import diplom.response.Blog;
import diplom.response.CalendarResponse;
import diplom.response.ResultResponse;
import diplom.response.StatisticResponse;
import diplom.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private final AuthService authService;

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
}
