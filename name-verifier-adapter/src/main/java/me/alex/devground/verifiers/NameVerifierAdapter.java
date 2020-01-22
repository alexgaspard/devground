package me.alex.devground.verifiers;

import me.alex.devground.ports.*;

public class NameVerifierAdapter implements NameVerifierService {

    @Override
    public boolean verifyName(String name) {
        name = name.trim();
        return name.length() != 0 && checkNameIsAlphabeticOnly(name);
    }

    private boolean checkNameIsAlphabeticOnly(String name) {
        return name.matches("[a-zA-Z ]+");
    }
}
