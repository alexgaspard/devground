package me.alex.devground.adapters;

import me.alex.devground.exceptions.*;
import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class OffersAdapter implements OfferCreator, OfferGetter, OffersLister, OfferCanceller {

    private final OffersFinder finder;
    private final OfferPersister persister;

    public OffersAdapter(OffersFinder finder, OfferPersister persister) {
        this.finder = finder;
        this.persister = persister;
    }

    @Override
    public String createOffer(Offer offer) throws NotCreated {
        if (offer.isExpired()) {
            throw new NotCreated();
        }
        return persister.persist(new Offer(offer, ""));
    }

    @Override
    public Optional<Offer> getOffer(String id) {
        return finder.find(id);
    }

    @Override
    public Collection<Offer> listOffers() {
        return finder.findAll();
    }

    @Override
    public void cancelOffer(String id) throws NotCancelled {
        final Offer offer = finder.find(id).orElseThrow(NotCancelled::new);
        if (offer.isExpired()) {
            throw new AlreadyExpired();
        }
        persister.persist(new Offer(offer, true));
    }
}
