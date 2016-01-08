package ua.net.aspebo.service;

import ua.net.aspebo.domain.ProductCategory;

import java.util.List;

/**
 * Service Interface for managing ProductCategory.
 */
public interface ProductCategoryService {

    /**
     * Save a productCategory.
     * @return the persisted entity
     */
    public ProductCategory save(ProductCategory productCategory);

    /**
     *  get all the productCategorys.
     *  @return the list of entities
     */
    public List<ProductCategory> findAll();

    /**
     *  get the "id" productCategory.
     *  @return the entity
     */
    public ProductCategory findOne(Long id);

    /**
     *  delete the "id" productCategory.
     */
    public void delete(Long id);

    /**
     * search for the productCategory corresponding
     * to the query.
     */
    public List<ProductCategory> search(String query);
}
