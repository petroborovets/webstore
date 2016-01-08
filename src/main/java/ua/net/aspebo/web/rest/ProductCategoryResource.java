package ua.net.aspebo.web.rest;

import com.codahale.metrics.annotation.Timed;
import ua.net.aspebo.domain.ProductCategory;
import ua.net.aspebo.service.ProductCategoryService;
import ua.net.aspebo.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProductCategory.
 */
@RestController
@RequestMapping("/api")
public class ProductCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryResource.class);
        
    @Inject
    private ProductCategoryService productCategoryService;
    
    /**
     * POST  /productCategorys -> Create a new productCategory.
     */
    @RequestMapping(value = "/productCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) throws URISyntaxException {
        log.debug("REST request to save ProductCategory : {}", productCategory);
        if (productCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("productCategory", "idexists", "A new productCategory cannot already have an ID")).body(null);
        }
        ProductCategory result = productCategoryService.save(productCategory);
        return ResponseEntity.created(new URI("/api/productCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("productCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productCategorys -> Updates an existing productCategory.
     */
    @RequestMapping(value = "/productCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody ProductCategory productCategory) throws URISyntaxException {
        log.debug("REST request to update ProductCategory : {}", productCategory);
        if (productCategory.getId() == null) {
            return createProductCategory(productCategory);
        }
        ProductCategory result = productCategoryService.save(productCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("productCategory", productCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productCategorys -> get all the productCategorys.
     */
    @RequestMapping(value = "/productCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductCategory> getAllProductCategorys() {
        log.debug("REST request to get all ProductCategorys");
        return productCategoryService.findAll();
            }

    /**
     * GET  /productCategorys/:id -> get the "id" productCategory.
     */
    @RequestMapping(value = "/productCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductCategory> getProductCategory(@PathVariable Long id) {
        log.debug("REST request to get ProductCategory : {}", id);
        ProductCategory productCategory = productCategoryService.findOne(id);
        return Optional.ofNullable(productCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productCategorys/:id -> delete the "id" productCategory.
     */
    @RequestMapping(value = "/productCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) {
        log.debug("REST request to delete ProductCategory : {}", id);
        productCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("productCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/productCategorys/:query -> search for the productCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/productCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProductCategory> searchProductCategorys(@PathVariable String query) {
        log.debug("Request to search ProductCategorys for query {}", query);
        return productCategoryService.search(query);
    }
}
