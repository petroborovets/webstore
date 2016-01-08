package ua.net.aspebo.service.impl;

import ua.net.aspebo.service.RatingService;
import ua.net.aspebo.domain.Rating;
import ua.net.aspebo.repository.RatingRepository;
import ua.net.aspebo.repository.search.RatingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Rating.
 */
@Service
@Transactional
public class RatingServiceImpl implements RatingService{

    private final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);
    
    @Inject
    private RatingRepository ratingRepository;
    
    @Inject
    private RatingSearchRepository ratingSearchRepository;
    
    /**
     * Save a rating.
     * @return the persisted entity
     */
    public Rating save(Rating rating) {
        log.debug("Request to save Rating : {}", rating);
        Rating result = ratingRepository.save(rating);
        ratingSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the ratings.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Rating> findAll() {
        log.debug("Request to get all Ratings");
        List<Rating> result = ratingRepository.findAll();
        return result;
    }

    /**
     *  get one rating by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Rating findOne(Long id) {
        log.debug("Request to get Rating : {}", id);
        Rating rating = ratingRepository.findOne(id);
        return rating;
    }

    /**
     *  delete the  rating by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.delete(id);
        ratingSearchRepository.delete(id);
    }

    /**
     * search for the rating corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Rating> search(String query) {
        
        log.debug("REST request to search Ratings for query {}", query);
        return StreamSupport
            .stream(ratingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
