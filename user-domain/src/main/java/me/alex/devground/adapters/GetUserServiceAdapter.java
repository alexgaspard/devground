package me.alex.devground.adapters;

import me.alex.devground.models.*;
import me.alex.devground.ports.*;

import java.util.*;

public class GetUserServiceAdapter implements GetUserService {

    private final UserRepository userRepository;
    private final NotifyService notifyService;

    public GetUserServiceAdapter(UserRepository userRepository, NotifyService notifyService) {
        this.userRepository = userRepository;
        this.notifyService = notifyService;
    }

    @Override
    public List<User> getAllUsers() {
        notifyService.notify("getAllUsers");
        return userRepository.getUsers();
    }

    @Override
    public Optional<User> getUser(int userId) {
        return userRepository.getUser(userId);
    }
}
