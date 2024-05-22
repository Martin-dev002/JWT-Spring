package practica.spring.crudbasico.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:4200") //url del front
                .allowedMethods("POST","GET","PUT","DELETE")
                .allowedHeaders("Origins", "Content-Type", "Accept", "Authorization")
                .allowCredentials(true)
                .maxAge(3600);


        registry.addMapping("/auth/**")
                .allowedOrigins("https://localhost:4200")//url del front
                .allowedMethods("POST","GET","PUT","DELETE")
                .allowedHeaders("Origins", "Content-Type", "Accept", "Authorization")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
