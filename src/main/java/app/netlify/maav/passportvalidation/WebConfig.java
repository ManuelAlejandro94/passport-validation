package app.netlify.maav.passportvalidation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/v1/passport-validation/**")
            .allowedOrigins("http://localhost:4200/", "https://passport-validation.netlify.app/")
            .allowedMethods("POST");
    }
}
