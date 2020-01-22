package me.alex.devground.controllers;

import me.alex.devground.api.*;
import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

import static me.alex.devground.controllers.UsersRestController.*;

@RestController
public class UsersRestControllerV1 {

    public static final String APPLICATION_USER_V1 = "application/me.alex.user-v1+json";

    private GetUserService getUserService;
    private AddUserService addUserService;

    @Autowired
    public UsersRestControllerV1(GetUserService getUserService, AddUserService addUserService) {
        this.getUserService = getUserService;
        this.addUserService = addUserService;
    }

    @Deprecated
    @DeprecatedAPI(location = APPLICATION_USER_V2)
    @GetMapping(value = "/users", produces = APPLICATION_USER_V1)
    public List<User> getUsers() {
        return getUserService.getAllUsers();
    }

    @Deprecated
    @DeprecatedAPI(location = APPLICATION_USER_V2)
    @GetMapping(value = "/users/{userid}", produces = APPLICATION_USER_V1)
    public ResponseEntity getUser(@PathVariable int userid) {
        Optional<User> possibleUser = getUserService.getUser(userid);
        if (possibleUser.isPresent()) {
            return new ResponseEntity<>(possibleUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new ErrorMessage("User with id " + userid + " was not found"),
                HttpStatus.NOT_FOUND
        );
    }

    @PostMapping(value = "/users", consumes = APPLICATION_USER_V1)
    public ResponseEntity addUser(@Valid @RequestBody User newUser) {
        int userId = addUserService.addUser(newUser);
        if (userId > 0) {
            return new ResponseEntity<>("{\"id\": " + userId + "}", HttpStatus.CREATED);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
