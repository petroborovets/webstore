package ua.net.aspebo.service.impl;

import ua.net.aspebo.service.ProductService;
import ua.net.aspebo.domain.Product;
import ua.net.aspebo.repository.ProductRepository;
import ua.net.aspebo.repository.search.ProductSearchRepository;
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
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    @Inject
    private ProductRepository productRepository;
    
    @Inject
    private ProductSearchRepository productSearchRepository;
    
    /**
     * Save a product.
     * @return the persisted entity
     */
    public Product save(Product product) {
        log.debug("Request to save Product : {}", product);
        Product result = productRepository.save(product);
        productSearchRepository.save(result);
        return result;
    }

    /**
     *  get all the products.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Product> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        Page<Product> result = productRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one product by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Product findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findOne(id);
        return product;
    }

    /**
     *  delete the  product by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
        productSearchRepository.delete(id);
    }

    /**
     * search for the product corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<Product> search(String query) {
        
        log.debug("REST request to search Products for query {}", query);
        return StreamSupport
            .stream(productSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
