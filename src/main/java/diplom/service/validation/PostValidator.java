package diplom.service.validation;

import diplom.request.PostForm;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {
    @Value("${validation.post.minTitleLength:3}")
    private int minTitleLength;
    @Value("${validation.post.minTextLength:50}")
    private int minTextLength;

    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostForm form = (PostForm) target;
        String text = Jsoup.parse(form.getText()).text();
        String title = form.getTitle();

        if (title.isEmpty()) {
            errors.reject("emptyTitle");
        } else if (title.length() < minTitleLength) {
            errors.reject("minTitle");
        }

        if (text.isEmpty()) {
            errors.reject("emptyText");
        } else if (text.length() < minTextLength) {
            errors.reject("minText");
        }
    }
}
