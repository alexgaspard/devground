package me.alex.devground.ports;

import me.alex.devground.models.*;

import java.util.*;

public interface UserRepository {

    int addUser(User newUser);

    List<User> getUsers();

    void deleteUser(int id);

    Optional<User> getUser(int userId);

}
