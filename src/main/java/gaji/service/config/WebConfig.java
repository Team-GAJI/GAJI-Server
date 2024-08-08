package gaji.service.config;

import gaji.service.domain.recruit.converter.CategoryConverter;
import gaji.service.domain.recruit.converter.FilterConverter;
import gaji.service.domain.recruit.converter.SortTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) { // Converter 등록
        registry.addConverter(new CategoryConverter());
        registry.addConverter(new PostTypeConverter());
        registry.addConverter(new FilterConverter());
        registry.addConverter(new SortTypeConverter());
    }
}