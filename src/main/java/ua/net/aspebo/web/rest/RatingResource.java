package ua.net.aspebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import ua.net.aspebo.domain.Rating;
import ua.net.aspebo.service.RatingService;
import ua.net.aspebo.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Rating.
 */
@RestController
@RequestMapping("/api")
public class RatingResource {

    private final Logger log = LoggerFactory.getLogger(RatingResource.class);
        
    @Inject
    private RatingService ratingService;
    
    /**
     * POST  /ratings -> Create a new rating.
     */
    @RequestMapping(value = "/ratings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rating> createRating(@Valid @RequestBody Rating rating) throws URISyntaxException {
        log.debug("REST request to save Rating : {}", rating);
        if (rating.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rating", "idexists", "A new rating cannot already have an ID")).body(null);
        }
        Rating result = ratingService.save(rating);
        return ResponseEntity.created(new URI("/api/ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rating", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ratings -> Updates an existing rating.
     */
    @RequestMapping(value = "/ratings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rating> updateRating(@Valid @RequestBody Rating rating) throws URISyntaxException {
        log.debug("REST request to update Rating : {}", rating);
        if (rating.getId() == null) {
            return createRating(rating);
        }
        Rating result = ratingService.save(rating);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rating", rating.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ratings -> get all the ratings.
     */
    @RequestMapping(value = "/ratings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Rating> getAllRatings() {
        log.debug("REST request to get all Ratings");
        return ratingService.findAll();
            }

    /**
     * GET  /ratings/:id -> get the "id" rating.
     */
    @RequestMapping(value = "/ratings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rating> getRating(@PathVariable Long id) {
        log.debug("REST request to get Rating : {}", id);
        Rating rating = ratingService.findOne(id);
        return Optional.ofNullable(rating)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ratings/:id -> delete the "id" rating.
     */
    @RequestMapping(value = "/ratings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        log.debug("REST request to delete Rating : {}", id);
        ratingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rating", id.toString())).build();
    }

    /**
     * SEARCH  /_search/ratings/:query -> search for the rating corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/ratings/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Rating> searchRatings(@PathVariable String query) {
        log.debug("Request to search Ratings for query {}", query);
        return ratingService.search(query);
    }
}
