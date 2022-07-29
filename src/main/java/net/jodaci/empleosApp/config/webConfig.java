package net.jodaci.empleosApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

    @Value("${empleosapp.ruta.imagenes}")
    private String rutaImagenes;
    private Environment env;
    private String rutaCv = "c:/empleosApp/files-cv/";
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/logos/**").addResourceLocations("file:/empleos/img-vacantes/"); // Linux
        //registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/empleosApp/img-vacantes/"); // Windows
        registry.addResourceHandler("/logos/**").addResourceLocations("file:"+rutaImagenes);

        // Configuración de los recursos estáticos (archivos de los CV)
        //registry.addResourceHandler("/cv/**").addResourceLocations("file:c:/empleosApp/files-cv/"); // Windows
        registry.addResourceHandler("/cv/**").addResourceLocations("file:/empleosApp/files-cv/");

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties =
                new PropertySourcesPlaceholderConfigurer();
        properties.setLocation(new FileSystemResource("/empleosApp/configs/application.properties"));
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }
}
