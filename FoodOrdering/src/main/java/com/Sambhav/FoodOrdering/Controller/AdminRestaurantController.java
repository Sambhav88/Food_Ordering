package com.Sambhav.FoodOrdering.Controller;

import com.Sambhav.FoodOrdering.Service.RestaurantService;
import com.Sambhav.FoodOrdering.Service.UserService;
import com.Sambhav.FoodOrdering.model.Restaurant;
import com.Sambhav.FoodOrdering.model.Users;
import com.Sambhav.FoodOrdering.request.CreateRestaurantRequest;
import com.Sambhav.FoodOrdering.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController
{
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(req,users);
        System.out.println("Restaurant ID before saving: " + restaurant.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
         restaurantService.deleteRestaurant(id);

        MessageResponse msg = new MessageResponse();
        msg.setMessage("Restaurant Is Deleted Successfully");
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> deleteRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantById(
            @RequestHeader("Authorization") String jwt)throws Exception
    {
        Users users = userService.findByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(users.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
