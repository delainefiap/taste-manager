package br.com.tastemanager.repository;

import br.com.tastemanager.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
//    List<Menu> findByRestaurantId(Long restaurantId);

    Optional<Menu> findByRestaurantId(Long restaurantId);

}