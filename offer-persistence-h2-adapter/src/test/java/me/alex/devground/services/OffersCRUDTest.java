package me.alex.devground.services;


import me.alex.devground.model.*;
import me.alex.devground.models.*;
import me.alex.devground.repositories.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

class OffersCRUDTest {

    private OffersRepository repository;
    private OffersCRUD crud;

    @BeforeEach
    void setUp() {
        repository = mock(OffersRepository.class);
        crud = new OffersCRUD(repository);
    }

    @Test
    void findShouldFindById() {
        final String id = "1";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        final OfferEntity entity = new OfferEntity(Long.parseLong(id), description, startDate, validityPeriod, currency, price, cancelled);
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        final Optional<Offer> offerOptional = crud.find(id);
        verify(repository).findById(Long.parseLong(id));
        assertTrue(offerOptional.isPresent());
        final Offer offer = offerOptional.get();
        assertEquals(id, offer.getId());
        assertEquals(description, offer.getDescription());
        assertEquals(startDate, offer.getStartDate());
        assertEquals(validityPeriod, offer.getValidityPeriod());
        assertEquals(currency, offer.getCurrency());
        assertEquals(price, offer.getPrice());
        assertEquals(cancelled, offer.isCancelled());
    }

    @Test
    void findWhenNonNumericIdShouldReturnEmpty() {
        final String id = "id";
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        final Optional<Offer> offerOptional = crud.find(id);
        verify(repository).findById(0L);
        assertFalse(offerOptional.isPresent());
    }

    @Test
    void findWhenEmptyIdShouldReturnEmpty() {
        final String id = "";
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        final Optional<Offer> offerOptional = crud.find(id);
        verify(repository).findById(0L);
        assertFalse(offerOptional.isPresent());
    }

    @Test
    void findAll() {
        final String id = "1";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        final OfferEntity entity = new OfferEntity(Long.parseLong(id), description, startDate, validityPeriod, currency, price, cancelled);
        when(repository.findAll()).thenReturn(Collections.singleton(entity));
        final Collection<Offer> offers = crud.findAll();
        verify(repository).findAll();
        assertEquals(1, offers.size());
        final Offer offer = offers.iterator().next();
        assertEquals(id, offer.getId());
        assertEquals(description, offer.getDescription());
        assertEquals(startDate, offer.getStartDate());
        assertEquals(validityPeriod, offer.getValidityPeriod());
        assertEquals(currency, offer.getCurrency());
        assertEquals(price, offer.getPrice());
        assertEquals(cancelled, offer.isCancelled());
    }

    @Test
    void persistShouldFindById() {
        final String id = "1";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        final long savedId = 10L;
        final Offer offer = new Offer(id, description, startDate, validityPeriod, currency, price, cancelled);
        when(repository.save(any())).thenReturn(new OfferEntity(savedId, null, null, null, null, 0, false));
        final String result = crud.persist(offer);
        assertEquals(Long.toString(savedId), result);
        final ArgumentCaptor<OfferEntity> captor = ArgumentCaptor.forClass(OfferEntity.class);
        verify(repository).save(captor.capture());
        final OfferEntity entity = captor.getValue();
        assertEquals(Long.parseLong(id), entity.getId());
        assertEquals(description, entity.getDescription());
        assertEquals(startDate, entity.getStartDate());
        assertEquals(validityPeriod, entity.getValidityPeriod());
        assertEquals(currency, entity.getCurrency());
        assertEquals(price, entity.getPrice());
        assertEquals(cancelled, entity.isCancelled());
    }

    @Test
    void persistWhenNonNumericIdShouldSaveWithIdToZero() {
        final String id = "id";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        final long savedId = 10L;
        final Offer offer = new Offer(id, description, startDate, validityPeriod, currency, price, cancelled);
        when(repository.save(any())).thenReturn(new OfferEntity(savedId, null, null, null, null, 0, false));
        final String result = crud.persist(offer);
        assertEquals(Long.toString(savedId), result);
        final ArgumentCaptor<OfferEntity> captor = ArgumentCaptor.forClass(OfferEntity.class);
        verify(repository).save(captor.capture());
        final OfferEntity entity = captor.getValue();
        assertEquals(0, entity.getId());
        assertEquals(description, entity.getDescription());
        assertEquals(startDate, entity.getStartDate());
        assertEquals(validityPeriod, entity.getValidityPeriod());
        assertEquals(currency, entity.getCurrency());
        assertEquals(price, entity.getPrice());
        assertEquals(cancelled, entity.isCancelled());
    }

    @Test
    void persistWhenEmptyIdShouldSaveWithIdToZero() {
        final String id = "";
        final String description = "description";
        final Instant startDate = Instant.now();
        final Duration validityPeriod = Duration.ofMinutes(1);
        final Currency currency = Currency.getInstance(Locale.CANADA);
        final double price = 1.2;
        final boolean cancelled = true;
        final long savedId = 10L;
        final Offer offer = new Offer(id, description, startDate, validityPeriod, currency, price, cancelled);
        when(repository.save(any())).thenReturn(new OfferEntity(savedId, null, null, null, null, 0, false));
        final String result = crud.persist(offer);
        assertEquals(Long.toString(savedId), result);
        final ArgumentCaptor<OfferEntity> captor = ArgumentCaptor.forClass(OfferEntity.class);
        verify(repository).save(captor.capture());
        final OfferEntity entity = captor.getValue();
        assertEquals(0, entity.getId());
        assertEquals(description, entity.getDescription());
        assertEquals(startDate, entity.getStartDate());
        assertEquals(validityPeriod, entity.getValidityPeriod());
        assertEquals(currency, entity.getCurrency());
        assertEquals(price, entity.getPrice());
        assertEquals(cancelled, entity.isCancelled());
    }
}