package net.javayum.patterns.app;

import net.javayum.patterns.springenvironment.DatabaseSpringEnvironmentConfiguration;
import net.javayum.patterns.springenvironment.web.jaxrs.spring.JAXRSConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        DatabaseSpringEnvironmentConfiguration.class,
        JAXRSConfiguration.class,
})
public class ApplicationConfiguration {
}
