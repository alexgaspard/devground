package me.alex.devground.controllers;


import me.alex.devground.exceptions.*;
import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import javax.validation.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

import static me.alex.devground.config.WebConfig.*;

@RestController
@RequestMapping(OffersController.OFFERS_PATH)
public class OffersController {

    public static final String APPLICATION_OFFER_V1 = "application/me.alex.offer-v1+json";
    static final String OFFERS_PATH = "/offers";
    static final String ID_PATH = "/{id}";

    private final OfferCreator offerCreator;
    private final OfferGetter offerGetter;
    private final OffersLister offersLister;
    private final OfferCanceller offerCanceller;

    public OffersController(OfferCreator offerCreator, OfferGetter offerGetter, OffersLister offersLister, OfferCanceller offerCanceller) {
        this.offerCreator = offerCreator;
        this.offerGetter = offerGetter;
        this.offersLister = offersLister;
        this.offerCanceller = offerCanceller;
    }

    @GetMapping(produces = {APPLICATION_OFFER_V1, APPLICATION_LATEST})
    public Collection<OfferJSON> listOffers() {
        return offersLister.listOffers().stream().map(this::convertToJSON).collect(Collectors.toList());
    }

    @GetMapping(value = ID_PATH, produces = APPLICATION_OFFER_V1)
    public OfferJSON getOffer(@PathVariable String id) {
        return offerGetter.getOffer(id).map(this::convertToJSON)
                .orElseThrow(NotFound::new);
    }

    @PostMapping
    public ResponseEntity<Void> createOffer(@RequestBody @Valid OfferJSON offer) {
        try {
            final String savedId = offerCreator.createOffer(convertToDomain(offer));
            final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(ID_PATH).buildAndExpand(savedId).toUri();
            return ResponseEntity.created(location).build();
        } catch (NotCreated e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping(ID_PATH + "/cancellation")
    public ResponseEntity<Void> cancelOffer(@PathVariable String id) {
        try {
            offerCanceller.cancelOffer(id);
        } catch (NotCancelled e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(OFFERS_PATH + ID_PATH).buildAndExpand(id).toUri();
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(location).build();
    }

    private OfferJSON convertToJSON(Offer offer) {
        return new OfferJSON(offer.getId(), offer.getDescription(), offer.getStartDate(), offer.getValidityPeriod(), offer.getCurrency(),
                offer.getPrice(), offer.isCancelled(), offer.isExpired());
    }

    private Offer convertToDomain(OfferJSON offer) {
        return new Offer(offer.getId(), offer.getDescription(), offer.getStartDate(), offer.getValidityPeriod(),
                offer.getCurrency(), offer.getPrice(), offer.isCancelled());
    }
}
