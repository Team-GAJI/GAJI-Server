package gaji.service.config;

import gaji.service.domain.post.converter.PostTypeConverter;
import gaji.service.domain.post.converter.SortTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PostTypeConverter());
        registry.addConverter(new SortTypeConverter());
    }
}
