package me.alex.devground.ports;

import me.alex.devground.models.*;

import java.util.*;

public interface OfferGetter {

    Optional<Offer> getOffer(String id);
}
