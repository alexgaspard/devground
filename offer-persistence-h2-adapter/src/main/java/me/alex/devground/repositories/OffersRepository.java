package me.alex.devground.repositories;

import me.alex.devground.model.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepository extends CrudRepository<OfferEntity, Long> {

}
