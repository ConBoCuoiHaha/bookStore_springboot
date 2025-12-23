package com.bookstorevn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path:src/main/resources/static/images/product}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path path = Paths.get(uploadPath);
        String absolutePath = path.toFile().getAbsolutePath();
        
        // Map /images/product/** to the actual file system path
        // This ensures uploaded images are served even if the server hasn't been recompiled/restarted
        registry.addResourceHandler("/images/product/**")
                .addResourceLocations("file:/" + absolutePath + "/");
                
        // Also ensure external static resources are handled if needed
        // registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
