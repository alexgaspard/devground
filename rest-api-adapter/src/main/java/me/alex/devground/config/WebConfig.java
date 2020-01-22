package me.alex.devground.config;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public static final String APPLICATION_LATEST = "application/me.alex.latest+json";

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.valueOf(APPLICATION_LATEST));
    }
}
