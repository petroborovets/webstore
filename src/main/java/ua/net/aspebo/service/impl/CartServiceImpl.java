package ua.net.aspebo.service.impl;

import ua.net.aspebo.service.CartService;
import ua.net.aspebo.domain.Cart;
import ua.net.aspebo.repository.CartRepository;
import ua.net.aspebo.repository.search.CartSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Cart.
 */
@Service
@Transactional
public class CartServiceImpl implements CartService{

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    
    @Inject
    private CartRepository cartRepository;
    
    @Inject
    private CartSearchRepository cartSearchRepository;
    
    /**
     * Save a cart.
     * @return the persisted entity
     */
    public Cart save(Cart cart) {
        log.debug("Request to save Cart : {}", cart);
        Cart result = cartRepository.save(cart);
        cartSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the carts.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Cart> findAll(Pageable pageable) {
        log.debug("Request to get all Carts");
        Page<Cart> result = cartRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one cart by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cart findOne(Long id) {
        log.debug("Request to get Cart : {}", id);
        Cart cart = cartRepository.findOne(id);
        return cart;
    }

    /**
     *  delete the  cart by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.delete(id);
        cartSearchRepository.delete(id);
    }

    /**
     * search for the cart corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Cart> search(String query) {
        
        log.debug("REST request to search Carts for query {}", query);
        return StreamSupport
            .stream(cartSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
