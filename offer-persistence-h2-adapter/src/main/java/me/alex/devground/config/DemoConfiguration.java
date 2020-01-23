package me.alex.devground.config;


import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.springframework.boot.*;
import org.springframework.context.annotation.*;

import java.time.*;
import java.util.*;

@Configuration
public class DemoConfiguration {

    @Bean
    public CommandLineRunner demoData(OfferPersister persister) {
        return args -> {
            persister.persist(new Offer("", "First generated offer (h2)", Instant.now(), Duration.ofMinutes(2), Currency.getInstance(Locale.UK), 12.34, false));
            persister.persist(new Offer("", "Second generated offer (h2)", Instant.now().minusSeconds(50), Duration.ofMinutes(1), Currency.getInstance(Locale.UK), 56.78, true));
        };
    }
}
