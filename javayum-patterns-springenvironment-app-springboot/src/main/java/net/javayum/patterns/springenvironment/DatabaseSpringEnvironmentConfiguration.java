package net.javayum.patterns.springenvironment;

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
public class DatabaseSpringEnvironmentConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DatabaseSpringEnvironmentConfiguration.class);

    @Autowired
    private Environment env;

    @PostConstruct
    public void initializeDatabasePropertySourceUsage() {
        MutablePropertySources propertySources = ((ConfigurableEnvironment) env).getPropertySources();

        try {
            //dataSource, Table Name, Key Column, Value Column
            DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(dataSource(), "TA_PROPERTIES", "KEY", "VALUE");

            //CommonsConfigurationFactoryBean comes from https://java.net/projects/springmodules/sources/svn/content/tags/release-0_8/projects/commons/src/java/org/springmodules/commons/configuration/CommonsConfigurationFactoryBean.java?rev=2110
            //Per https://jira.spring.io/browse/SPR-10213 I chose to just copy the raw source into our project
            CommonsConfigurationFactoryBean commonsConfigurationFactoryBean = new CommonsConfigurationFactoryBean(databaseConfiguration);

            Properties dbProps = (Properties) commonsConfigurationFactoryBean.getObject();
            PropertiesPropertySource dbPropertySource = new PropertiesPropertySource("dbPropertySource", dbProps);

            //By being First, Database Properties take precedence over all other properties that have the same key name
            //You could put this last, or just in front of the application.properties if you wanted to...
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
