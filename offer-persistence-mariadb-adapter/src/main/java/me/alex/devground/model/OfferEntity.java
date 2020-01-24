package me.alex.devground.model;


import javax.persistence.*;
import java.time.*;
import java.util.*;

@Entity
public class OfferEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private final long id;
    private final Instant startDate;

    private final Duration validityPeriod;
    private final Currency currency;
    private final double price;
    private final boolean cancelled;

    private final String description;

    OfferEntity() {
        this(0, "", Instant.now(), Duration.ZERO, Currency.getInstance(Locale.UK), 0, false);
    }

    public OfferEntity(long id, String description, Instant startDate, Duration validityPeriod, Currency currency, double price, boolean cancelled) {
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.validityPeriod = validityPeriod;
        this.currency = currency;
        this.price = price;
        this.cancelled = cancelled;
    }

    public long getId() {
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
}
