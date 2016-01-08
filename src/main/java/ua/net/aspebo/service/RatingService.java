package ua.net.aspebo.service;

import ua.net.aspebo.domain.Rating;

import java.util.List;

/**
 * Service Interface for managing Rating.
 */
public interface RatingService {

    /**
     * Save a rating.
     * @return the persisted entity
     */
    public Rating save(Rating rating);

    /**
     *  get all the ratings.
     *  @return the list of entities
     */
    public List<Rating> findAll();

    /**
     *  get the "id" rating.
     *  @return the entity
     */
    public Rating findOne(Long id);

    /**
     *  delete the "id" rating.
     */
    public void delete(Long id);

    /**
     * search for the rating corresponding
     * to the query.
     */
    public List<Rating> search(String query);
}
