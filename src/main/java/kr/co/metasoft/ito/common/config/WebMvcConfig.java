package kr.co.metasoft.ito.common.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .resourceChain(true)
            .addResolver(resourceResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.removeConvertible(String.class, Collection.class);
    }

    private ResourceResolver resourceResolver() {
        return new PathResourceResolver() {
            @Override
            protected Resource getResource(
                    String resourcePath,
                    Resource location)
                            throws
                                IOException {
                Resource requestedResource = location.createRelative(resourcePath);
                Resource indexResource = new ClassPathResource("/static/index.html");
                return requestedResource.exists() && requestedResource.isReadable()
                        ? requestedResource : indexResource;
            }
        };
    }
}