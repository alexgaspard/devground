package me.alex.devground.models;

import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

public class OfferJSON {

    private final String id;
    @NotEmpty
    private final String description;
    @NotNull
    private final Instant startDate;
    @NotNull
    private final Duration validityPeriod;
    @NotNull
    private final Currency currency;
    @NotNull
    private final double price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final boolean cancelled;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final boolean expired;

    public OfferJSON(String id, String description, Instant startDate, Duration validityPeriod, Currency currency, double price, boolean cancelled, boolean expired) {
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.validityPeriod = validityPeriod;
        this.currency = currency;
        this.price = price;
        this.cancelled = cancelled;
        this.expired = expired;
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
        return expired;
    }
}
