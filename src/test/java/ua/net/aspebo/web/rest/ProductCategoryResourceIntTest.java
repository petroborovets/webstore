package ua.net.aspebo.web.rest;

import ua.net.aspebo.Application;
import ua.net.aspebo.domain.ProductCategory;
import ua.net.aspebo.repository.ProductCategoryRepository;
import ua.net.aspebo.service.ProductCategoryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProductCategoryResource REST controller.
 *
 * @see ProductCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ProductCategoryRepository productCategoryRepository;

    @Inject
    private ProductCategoryService productCategoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductCategoryMockMvc;

    private ProductCategory productCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductCategoryResource productCategoryResource = new ProductCategoryResource();
        ReflectionTestUtils.setField(productCategoryResource, "productCategoryService", productCategoryService);
        this.restProductCategoryMockMvc = MockMvcBuilders.standaloneSetup(productCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        productCategory = new ProductCategory();
        productCategory.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProductCategory() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // Create the ProductCategory

        restProductCategoryMockMvc.perform(post("/api/productCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productCategory)))
                .andExpect(status().isCreated());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategorys = productCategoryRepository.findAll();
        assertThat(productCategorys).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategory testProductCategory = productCategorys.get(productCategorys.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategorys() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategorys
        restProductCategoryMockMvc.perform(get("/api/productCategorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/productCategorys/{id}", productCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(productCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductCategory() throws Exception {
        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/productCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

		int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory
        productCategory.setName(UPDATED_NAME);

        restProductCategoryMockMvc.perform(put("/api/productCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productCategory)))
                .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategorys = productCategoryRepository.findAll();
        assertThat(productCategorys).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategorys.get(productCategorys.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

		int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        // Get the productCategory
        restProductCategoryMockMvc.perform(delete("/api/productCategorys/{id}", productCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductCategory> productCategorys = productCategoryRepository.findAll();
        assertThat(productCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
