package ua.net.aspebo.service;

import ua.net.aspebo.domain.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Cart.
 */
public interface CartService {

    /**
     * Save a cart.
     * @return the persisted entity
     */
    public Cart save(Cart cart);

    /**
     *  get all the carts.
     *  @return the list of entities
     */
    public Page<Cart> findAll(Pageable pageable);

    /**
     *  get the "id" cart.
     *  @return the entity
     */
    public Cart findOne(Long id);

    /**
     *  delete the "id" cart.
     */
    public void delete(Long id);

    /**
     * search for the cart corresponding
     * to the query.
     */
    public List<Cart> search(String query);
}
