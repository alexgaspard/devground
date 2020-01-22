package me.alex.devground.controllers;


import me.alex.devground.exceptions.*;
import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.mock.web.*;
import org.springframework.web.context.request.*;

import java.net.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OffersControllerTest {

    public static final String HTTP_LOCALHOST = "http://localhost";
    private OfferCreator offerCreator;
    private OfferGetter offerGetter;
    private OffersLister offersLister;
    private OfferCanceller offerCanceller;
    private OffersController controller;

    @BeforeEach
    void setUp() {
        offerCreator = mock(OfferCreator.class);
        offerGetter = mock(OfferGetter.class);
        offersLister = mock(OffersLister.class);
        offerCanceller = mock(OfferCanceller.class);
        controller = new OffersController(offerCreator, offerGetter, offersLister, offerCanceller);
    }

    @Test
    void listOffersShouldListOffers() {
        final String id = "id";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        when(offersLister.listOffers()).thenReturn(Collections.singleton(new Offer(id, description, startDate, validityPeriod, currency, price, cancelled)));
        final Collection<OfferJSON> jsons = controller.listOffers();
        verify(offersLister).listOffers();
        assertEquals(1, jsons.size());
        final OfferJSON offer = jsons.iterator().next();
        assertEquals(id, offer.getId());
        assertEquals(description, offer.getDescription());
        assertEquals(startDate, offer.getStartDate());
        assertEquals(validityPeriod, offer.getValidityPeriod());
        assertEquals(currency, offer.getCurrency());
        assertEquals(price, offer.getPrice());
        assertEquals(cancelled, offer.isCancelled());
    }

    @Test
    void getOfferShouldFindOffer() {
        final String id = "id";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        when(offerGetter.getOffer(any())).thenReturn(Optional.of(new Offer(id, description, startDate, validityPeriod, currency, price, cancelled)));
        final OfferJSON offer = controller.getOffer(id);
        verify(offerGetter).getOffer(id);
        assertEquals(id, offer.getId());
        assertEquals(description, offer.getDescription());
        assertEquals(startDate, offer.getStartDate());
        assertEquals(validityPeriod, offer.getValidityPeriod());
        assertEquals(currency, offer.getCurrency());
        assertEquals(price, offer.getPrice());
        assertEquals(cancelled, offer.isCancelled());
    }

    @Test
    void getOfferWhenEmptyShouldThrowNotFound() {
        final String id = "id";
        when(offerGetter.getOffer(any())).thenReturn(Optional.empty());
        assertThrows(NotFound.class, () -> controller.getOffer(id));
        verify(offerGetter).getOffer(id);
    }

    @Test
    void createOfferShouldCreate() throws NotCreated {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        final String id = "id";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        final String savedId = "savedId";
        when(offerCreator.createOffer(any())).thenReturn(savedId);
        final ResponseEntity<Void> response = controller.createOffer(new OfferJSON(id, description, startDate, validityPeriod, currency, price, cancelled, false));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(URI.create(HTTP_LOCALHOST + "/" + savedId), response.getHeaders().getLocation());
        final ArgumentCaptor<Offer> captor = ArgumentCaptor.forClass(Offer.class);
        verify(offerCreator).createOffer(captor.capture());
        assertEquals(id, captor.getValue().getId());
        assertEquals(description, captor.getValue().getDescription());
        assertEquals(startDate, captor.getValue().getStartDate());
        assertEquals(validityPeriod, captor.getValue().getValidityPeriod());
        assertEquals(currency, captor.getValue().getCurrency());
        assertEquals(price, captor.getValue().getPrice());
        assertEquals(cancelled, captor.getValue().isCancelled());
    }

    @Test
    void createOfferWhenNotCreatedShouldReturnBadRequest() throws NotCreated {
        final String id = "id";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        when(offerCreator.createOffer(any())).thenThrow(new NotCreated());
        final ResponseEntity<Void> response = controller.createOffer(new OfferJSON(id, description, startDate, validityPeriod, currency, price, cancelled, false));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(offerCreator).createOffer(any());
    }

    @Test
    void cancelOfferShouldCancel() throws NotCancelled {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        final String id = "id";
        final ResponseEntity<Void> response = controller.cancelOffer(id);
        assertEquals(HttpStatus.SEE_OTHER, response.getStatusCode());
        assertEquals(URI.create(HTTP_LOCALHOST + OffersController.OFFERS_PATH + "/" + id), response.getHeaders().getLocation());
        verify(offerCanceller).cancelOffer(id);
    }

    @Test
    void cancelOfferWhenNotCancelledShouldReturnNotModified() throws NotCancelled {
        final String id = "id";
        doThrow(new NotCancelled()).when(offerCanceller).cancelOffer(id);
        final ResponseEntity<Void> response = controller.cancelOffer(id);
        assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
        verify(offerCanceller).cancelOffer(id);
    }
}