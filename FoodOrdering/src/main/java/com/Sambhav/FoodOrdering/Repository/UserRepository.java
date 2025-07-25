package com.Sambhav.FoodOrdering.Repository;

import com.Sambhav.FoodOrdering.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long>
{
    public Users findByEmail(String username);

}
