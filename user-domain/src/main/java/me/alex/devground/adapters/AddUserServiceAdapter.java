package me.alex.devground.adapters;

import me.alex.devground.models.*;
import me.alex.devground.ports.*;

public class AddUserServiceAdapter implements AddUserService {

    private NameVerifierService nameVerifierService;

    private UserRepository userRepository;

    public AddUserServiceAdapter(NameVerifierService nameVerifierService,
                                 UserRepository userRepository) {
        this.nameVerifierService = nameVerifierService;
        this.userRepository = userRepository;
    }

    @Override
    public int addUser(User newUser) {
        if (newUser.getName().length() != 0 && nameVerifierService.verifyName(newUser.getName())) {
            return userRepository.addUser(newUser);
        }

        return -1;
    }
}
