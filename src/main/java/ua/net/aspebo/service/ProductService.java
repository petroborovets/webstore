package ua.net.aspebo.service;

import ua.net.aspebo.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Product.
 */
public interface ProductService {

    /**
     * Save a product.
     * @return the persisted entity
     */
    public Product save(Product product);

    /**
     *  get all the products.
     *  @return the list of entities
     */
    public Page<Product> findAll(Pageable pageable);

    /**
     *  get the "id" product.
     *  @return the entity
     */
    public Product findOne(Long id);

    /**
     *  delete the "id" product.
     */
    public void delete(Long id);

    /**
     * search for the product corresponding
     * to the query.
     */
    public List<Product> search(String query);
}
