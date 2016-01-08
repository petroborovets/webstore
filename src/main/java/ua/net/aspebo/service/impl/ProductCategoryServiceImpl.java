package ua.net.aspebo.service.impl;

import ua.net.aspebo.service.ProductCategoryService;
import ua.net.aspebo.domain.ProductCategory;
import ua.net.aspebo.repository.ProductCategoryRepository;
import ua.net.aspebo.repository.search.ProductCategorySearchRepository;
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
 * Service Implementation for managing ProductCategory.
 */
@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService{

    private final Logger log = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
    
    @Inject
    private ProductCategoryRepository productCategoryRepository;
    
    @Inject
    private ProductCategorySearchRepository productCategorySearchRepository;
    
    /**
     * Save a productCategory.
     * @return the persisted entity
     */
    public ProductCategory save(ProductCategory productCategory) {
        log.debug("Request to save ProductCategory : {}", productCategory);
        ProductCategory result = productCategoryRepository.save(productCategory);
        productCategorySearchRepository.save(result);
        return result;
    }

    /**
     *  get all the productCategorys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ProductCategory> findAll() {
        log.debug("Request to get all ProductCategorys");
        List<ProductCategory> result = productCategoryRepository.findAll();
        return result;
    }

    /**
     *  get one productCategory by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProductCategory findOne(Long id) {
        log.debug("Request to get ProductCategory : {}", id);
        ProductCategory productCategory = productCategoryRepository.findOne(id);
        return productCategory;
    }

    /**
     *  delete the  productCategory by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductCategory : {}", id);
        productCategoryRepository.delete(id);
        productCategorySearchRepository.delete(id);
    }

    /**
     * search for the productCategory corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<ProductCategory> search(String query) {
        
        log.debug("REST request to search ProductCategorys for query {}", query);
        return StreamSupport
            .stream(productCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
