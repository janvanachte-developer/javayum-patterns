package net.javayum.patterns.web.jaxrs.spring;

import java.util.concurrent.atomic.AtomicLong;

import net.javayum.patterns.domain.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/property")
public class PropertyServiceImpl {

    @Autowired
    private Environment environment;

    @RequestMapping(path = "/{key:.+}", method = RequestMethod.GET) // ":.+ added because of Spring bug truncating PathVariable with dots. See https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    public PropertyDTO getByKey(@PathVariable String key) {


        return new PropertyDTO(key, environment.getProperty(key));
    }
}

