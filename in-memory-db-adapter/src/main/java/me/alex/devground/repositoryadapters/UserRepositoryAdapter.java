package me.alex.devground.repositoryadapters;

import me.alex.devground.models.*;
import me.alex.devground.ports.*;

import java.util.*;

public class UserRepositoryAdapter implements UserRepository {

    private List<User> users = new ArrayList<>();

    private int nextAvailableId = 1;

    @Override
    public int addUser(User newUser) {
        final User savedUser = new User(newUser, nextAvailableId);
        users.add(savedUser);

        incrementNextAvailableId();

        return savedUser.getId();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void deleteUser(int id) {
        for (int index = 0; index < users.size(); index++) {
            if (users.get(index).getId() == id) {
                users.remove(index);
                return;
            }
        }
    }

    @Override
    public Optional<User> getUser(int userId) {
        return users.stream().filter(user -> user.getId() == userId).findFirst();
    }

    private void incrementNextAvailableId() {
        nextAvailableId++;
    }
}
