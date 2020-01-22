package me.alex.devground.ports;

import me.alex.devground.models.*;

public interface OfferPersister {

    String persist(Offer offer);
}
