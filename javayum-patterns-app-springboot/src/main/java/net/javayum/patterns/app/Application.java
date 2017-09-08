package net.javayum.patterns.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class Application {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";
    private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";
    private static final String SPRING_PROFILE_DEVELOPMENT = "net.javayum.common.infra.spring.profile.development";
    private static final String SPRING_PROFILE_PRODUCTION = "net.javayum.common.infra.,spring.profile.production";

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final Environment environment;

    public static void main(String[] args) {

        ApplicationContext applicationContext;

        // minimalistic: applicationContext = SpringApplication.run(Application.class, args);

        // more elaborate
        SpringApplication springApplication = new SpringApplicationBuilder()
                //.bannerMode(Banner.Mode.OFF)
                // Banner in banner.txt
                .sources(Application.class)
                .build();

        applicationContext = springApplication.run(args);

        // applicztion.properties is read by defaultr
        Environment environment = applicationContext.getEnvironment();
        logger.info(environment.getProperty("net.javayum.pattern.app.springboot.property"));

        //active profiles are set in application.properties
        Collection<String> profiles = Arrays.asList(environment.getActiveProfiles());
        for (String profile : profiles) {
            logger.info("Profie '{}' is active", profile);
        }
    }


    //optional
    public Application(Environment environment) {

        this.environment = environment;
    }

    //optional
    @PostConstruct
    public void checkProfiles() {
        Collection<String> profiles = Arrays.asList(environment.getActiveProfiles());
        if (profiles.contains(SPRING_PROFILE_DEVELOPMENT) && profiles.contains(SPRING_PROFILE_PRODUCTION)) {
            logger.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
        }
    }
}

