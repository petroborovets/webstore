package ua.net.aspebo.repository;

import ua.net.aspebo.domain.Cart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cart entity.
 */
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("select cart from Cart cart where cart.user.login = ?#{principal.username}")
    List<Cart> findByUserIsCurrentUser();

}
