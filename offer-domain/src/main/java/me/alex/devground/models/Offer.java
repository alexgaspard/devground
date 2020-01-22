package me.alex.devground.models;

import java.time.*;
import java.util.*;

public class Offer {

    private final String id;
    private final String description;
    private final Instant startDate;
    private final Duration validityPeriod;
    private final Currency currency;
    private final double price;
    private final boolean cancelled;

    public Offer(Offer offer, String id) {
        this(id, offer.getDescription(), offer.getStartDate(), offer.getValidityPeriod(), offer.getCurrency(), offer.getPrice(), offer.isCancelled());
    }

    public Offer(Offer offer, boolean cancelled) {
        this(offer.getId(), offer.getDescription(), offer.getStartDate(), offer.getValidityPeriod(), offer.getCurrency(), offer.getPrice(), cancelled);
    }

    public Offer(String id, String description, Instant startDate, Duration validityPeriod, Currency currency, double price, boolean cancelled) {
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.validityPeriod = validityPeriod;
        this.currency = currency;
        this.price = price;
        this.cancelled = cancelled;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Duration getValidityPeriod() {
        return validityPeriod;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getPrice() {
        return price;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isExpired() {
        return startDate.plus(validityPeriod).isBefore(Instant.now());
    }
}
