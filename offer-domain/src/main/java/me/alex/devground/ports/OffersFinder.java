package me.alex.devground.ports;


import me.alex.devground.models.*;

import java.util.*;

public interface OffersFinder {

    Optional<Offer> find(String id);

    Collection<Offer> findAll();
}
