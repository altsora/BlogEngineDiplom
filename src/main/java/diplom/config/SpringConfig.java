package diplom.config;

import diplom.response.Blog;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    @ConfigurationProperties(prefix = "blog")
    public Blog blog() {
        return new Blog();
    }
}
