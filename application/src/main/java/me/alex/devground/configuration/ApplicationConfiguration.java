package me.alex.devground.configuration;

import me.alex.devground.*;
import me.alex.devground.adapters.*;
import me.alex.devground.ports.*;
import me.alex.devground.repositoryadapters.*;
import me.alex.devground.verifiers.*;
import org.springframework.context.annotation.*;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public AddUserService addUserService(NameVerifierService nameVerifierService, UserRepository userRepository) {
        return new AddUserServiceAdapter(nameVerifierService, userRepository);
    }

    @Bean
    public GetUserService getUserService(UserRepository userRepository, NotifyService notifyService) {
        return new GetUserServiceAdapter(userRepository, notifyService);
    }

    @Bean
    public NotifyService notifyService(Printer printer) {
        return new NotifyAdapter(printer);
    }

    @Bean
    public Printer printer() {
        return new PrinterAdapter();
    }

    @Bean
    public NameVerifierService nameVerifierService() {
        return new NameVerifierAdapter();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryAdapter();
    }
}
