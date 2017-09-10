package net.javayum.patterns.springenvironment.web.jaxrs.spring;

import net.javayum.patterns.springenvironment.domain.Property;
import net.javayum.patterns.springenvironment.domain.PropertyDTO;
import net.javayum.patterns.springenvironment.domain.spring.UpdateablePropertiesPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/property")
public class PropertyServiceImpl {

    @Autowired
    private ConfigurableEnvironment environment;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(path = "/{key:.+}", method = RequestMethod.GET)
    // ":.+ added because of Spring bug truncating PathVariable with dots. See https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    public Property getByKey(@PathVariable String key) {

        return new PropertyDTO(key, environment.getProperty(key));
    }

    @RequestMapping(path = "/{key:.+}", method = RequestMethod.POST)
    // ":.+ added because of Spring bug truncating PathVariable with dots. See https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    public ResponseEntity<?> save(@PathVariable String key, @RequestBody PropertyDTO property) {

        if (key.equals(property.getKey())) {
            MutablePropertySources propertySources = environment.getPropertySources();

            UpdateablePropertiesPropertySource propertySource = (UpdateablePropertiesPropertySource) propertySources.get("dbPropertySource");
            propertySource.update(property.getKey(), property.getValue());

            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

}

