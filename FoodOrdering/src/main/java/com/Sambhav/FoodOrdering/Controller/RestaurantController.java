package com.Sambhav.FoodOrdering.Controller;

import com.Sambhav.FoodOrdering.Dto.RestaurantDto;
import com.Sambhav.FoodOrdering.Service.RestaurantService;
import com.Sambhav.FoodOrdering.Service.UserService;
import com.Sambhav.FoodOrdering.model.Restaurant;
import com.Sambhav.FoodOrdering.model.Users;
import com.Sambhav.FoodOrdering.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController
{
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword) throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
            @RequestHeader("Authorization") String jwt) throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id )throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @PutMapping("/{id}/add-favourites")
    public ResponseEntity<RestaurantDto> addToFavourites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id )throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
        RestaurantDto restaurantdto = restaurantService.addFavourites(id,users);
        return new ResponseEntity<>(restaurantdto, HttpStatus.OK);
    }

}
