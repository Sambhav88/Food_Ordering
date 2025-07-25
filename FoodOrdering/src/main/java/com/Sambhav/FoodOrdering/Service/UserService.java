package com.Sambhav.FoodOrdering.Service;

import com.Sambhav.FoodOrdering.model.Users;

public interface UserService
{
    public Users findByJwtToken(String jwt) throws Exception;

    public Users findUserByEmail(String email) throws Exception;

}
