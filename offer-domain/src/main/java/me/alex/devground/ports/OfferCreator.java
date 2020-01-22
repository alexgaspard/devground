package me.alex.devground.ports;


import me.alex.devground.exceptions.*;
import me.alex.devground.models.*;

public interface OfferCreator {

    String createOffer(Offer offer) throws NotCreated;
}
