package com.Sambhav.FoodOrdering.Service;

import com.Sambhav.FoodOrdering.Dto.RestaurantDto;
import com.Sambhav.FoodOrdering.model.Restaurant;
import com.Sambhav.FoodOrdering.model.Users;
import com.Sambhav.FoodOrdering.request.CreateRestaurantRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RestaurantService
{
    public Restaurant createRestaurant(CreateRestaurantRequest request, Users users);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addFavourites(Long RestaurantId,Users users)throws Exception;

    public Restaurant updateRestaurantStatus(Long id)throws Exception;

}
