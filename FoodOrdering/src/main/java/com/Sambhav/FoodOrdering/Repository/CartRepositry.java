package com.Sambhav.FoodOrdering.Repository;

import com.Sambhav.FoodOrdering.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepositry extends JpaRepository<Cart,Long>
{

//    void save(Cart cart);
}
