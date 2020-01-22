package me.alex.devground.controllers;

import me.alex.devground.config.*;
import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;
import java.util.stream.*;

@RestController
public class UsersRestController {

    public static final String APPLICATION_USER_V2 = "application/me.alex.user-v2+json";

    private GetUserService getUserService;
    private AddUserService addUserService;

    UsersRestController(GetUserService getUserService, AddUserService addUserService) {
        this.getUserService = getUserService;
        this.addUserService = addUserService;
    }

    @GetMapping(value = "/users", produces = {APPLICATION_USER_V2, WebConfig.APPLICATION_LATEST})
    public List<User> getUsers() {
        return getUserService.getAllUsers().stream().map(user -> new User(user, "Sir " + user.getName())).collect(Collectors.toList());
    }

    @GetMapping(value = "/users/{userid}", produces = APPLICATION_USER_V2)
    public User getUser(@PathVariable int userid) {
        return getUserService.getUser(userid)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userid + " was not found"));
    }

    @PostMapping(value = "/users", consumes = APPLICATION_USER_V2)
    public ResponseEntity<?> addUser(@Valid @RequestBody User newUser) {
        int userId = addUserService.addUser(new User(newUser, newUser.getName().toUpperCase()));
        if (userId > 0) {
            return new ResponseEntity<>("{\"id\": " + userId + "}", HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NoSuchElementException e) {
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
