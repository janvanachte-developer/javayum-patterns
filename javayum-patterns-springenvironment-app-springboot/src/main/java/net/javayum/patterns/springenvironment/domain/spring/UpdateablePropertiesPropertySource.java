package net.javayum.patterns.springenvironment.domain.spring;

import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public class UpdateablePropertiesPropertySource extends PropertiesPropertySource{

    public UpdateablePropertiesPropertySource(String name, Properties source) {
        super(name, source);
    }

    public void update(String key, String value) {

        source.replace(key, value);
    }
}
