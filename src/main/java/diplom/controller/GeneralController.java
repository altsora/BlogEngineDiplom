package diplom.controller;

import diplom.response.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeneralController {
    private final Blog blog;

    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("init")
    public Blog init() {
        return blog;
    }

    @GetMapping("/settings")
    public Map<String, Boolean> getSettings() {
        return null;
    }

    @GetMapping(value = "/tag")
    public String getTagList() {
        return null;
    }
}
