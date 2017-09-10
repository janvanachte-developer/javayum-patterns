package net.javayum.patterns.springenvironment;

import net.javayum.patterns.springenvironment.domain.spring.UpdateablePropertiesPropertySource;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springmodules.commons.configuration.CommonsConfigurationFactoryBean;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound=true)
public class DatabaseBackedSpringEnvironmentConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DatabaseBackedSpringEnvironmentConfiguration.class);

    @Autowired
    private Environment env;

    @PostConstruct
    public void initializeDatabasePropertySourceUsage() {

        MutablePropertySources propertySources = ((ConfigurableEnvironment) env).getPropertySources();

        try {
            DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(dataSource(), "TA_PROPERTIES", "KEY", "VALUE");

            CommonsConfigurationFactoryBean commonsConfigurationFactoryBean = new CommonsConfigurationFactoryBean(databaseConfiguration);

            Properties dbProps = (Properties) commonsConfigurationFactoryBean.getObject();

            PropertiesPropertySource dbPropertySource = new UpdateablePropertiesPropertySource("dbPropertySource", dbProps);

            propertySources.addFirst(dbPropertySource);
        } catch (Exception e) {
            log.error("Error during database properties setup", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Read why this is required: http://www.baeldung.com/2012/02/06/properties-with-spring/#java
     * It is important to be static: http://www.java-allandsundry.com/2013/07/spring-bean-and-propertyplaceholderconf.html
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource()
    {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
                .addScript("db/TA_PROPERTIES.sql")
                .build();
        return db;    }

}
