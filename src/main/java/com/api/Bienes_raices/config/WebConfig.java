package com.api.Bienes_raices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/uploads/**")
                .addResourceLocations("file:img/uploads/") // Busca en la carpeta raíz 'img' y luego 'uploads'
                .setCachePeriod(0);
    }

}
