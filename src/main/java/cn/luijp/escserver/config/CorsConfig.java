package cn.luijp.escserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${esc.cors-domain:}")
    private String corsDomain;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        if (Objects.equals(corsDomain, "")) {
            registry.addMapping("/**")
                    .allowedMethods("GET", "POST")
                    .allowedHeaders("*");
        } else {
            registry.addMapping("/**")
                    .allowedMethods("GET", "POST")
                    .allowCredentials(true)
                    .allowedOrigins(corsDomain)
                    .allowedHeaders("*");

        }

    }
}
