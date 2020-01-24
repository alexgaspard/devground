package me.alex.devground.config;


import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

import java.time.*;
import java.util.*;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"me.alex.devground"})
@EntityScan(basePackages = {"me.alex.devground.model"})
@EnableJpaRepositories(basePackages = "me.alex.devground.repositories")
public class DemoConfiguration {

    @Bean
    public CommandLineRunner demoData(OfferPersister persister) {
        return args -> {
            persister.persist(new Offer("1", "First generated offer (mariadb)", Instant.now(), Duration.ofMinutes(2), Currency.getInstance(Locale.UK), 12.34, false));
            persister.persist(new Offer("2", "Second generated offer (mariadb)", Instant.now().minusSeconds(50), Duration.ofMinutes(1), Currency.getInstance(Locale.UK), 56.78, true));
        };
    }
}
