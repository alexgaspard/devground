package me.alex.devground.services;


import me.alex.devground.model.*;
import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import me.alex.devground.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class OffersCRUD implements OfferPersister, OffersFinder {

    private final OffersRepository repository;

    public OffersCRUD(OffersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Offer> find(String id) {
        return repository.findById(convertIdFromStringToLong(id)).map(this::convertToOffer);
    }

    @Override
    public Collection<Offer> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::convertToOffer)
                .collect(Collectors.toList());
    }

    @Override
    public String persist(Offer offer) {
        return Long.toString(repository.save(new OfferEntity(convertIdFromStringToLong(offer.getId()), offer.getDescription(), offer.getStartDate(),
                offer.getValidityPeriod(), offer.getCurrency(), offer.getPrice(), offer.isCancelled())).getId());
    }

    private long convertIdFromStringToLong(String id) {
        if (id.isEmpty()) {
            return 0;
        }
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Offer convertToOffer(OfferEntity entity) {
        return new Offer(Long.toString(entity.getId()), entity.getDescription(), entity.getStartDate(), entity.getValidityPeriod(),
                entity.getCurrency(), entity.getPrice(), entity.isCancelled());
    }
}
