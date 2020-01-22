package me.alex.devground.ports;


import me.alex.devground.exceptions.*;

public interface OfferCanceller {

    void cancelOffer(String id) throws NotCancelled;
}
