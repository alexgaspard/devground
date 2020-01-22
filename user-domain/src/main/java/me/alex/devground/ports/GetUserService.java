package me.alex.devground.ports;

import me.alex.devground.models.*;

import java.util.*;

public interface GetUserService {

    List<User> getAllUsers();

    Optional<User> getUser(int userId);

}
