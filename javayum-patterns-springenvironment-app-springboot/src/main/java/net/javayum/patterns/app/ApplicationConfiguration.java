package net.javayum.patterns.app;

import net.javayum.patterns.web.jaxrs.spring.JAXRSConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        JAXRSConfiguration.class
})
public class ApplicationConfiguration {
}
