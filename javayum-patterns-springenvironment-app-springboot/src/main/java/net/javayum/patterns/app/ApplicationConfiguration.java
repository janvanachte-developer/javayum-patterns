package net.javayum.patterns.app;

import net.javayum.patterns.springenvironment.DatabaseBackedSpringEnvironmentConfiguration;
import net.javayum.patterns.springenvironment.web.jaxrs.spring.SpringJAXRSPropertyServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        DatabaseBackedSpringEnvironmentConfiguration.class,
        SpringJAXRSPropertyServiceConfiguration.class,
})
public class ApplicationConfiguration {
}
