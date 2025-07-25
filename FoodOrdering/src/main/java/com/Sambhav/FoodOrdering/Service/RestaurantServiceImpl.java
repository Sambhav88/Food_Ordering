package com.Sambhav.FoodOrdering.Service;

import com.Sambhav.FoodOrdering.Dto.RestaurantDto;
import com.Sambhav.FoodOrdering.Repository.AddressRepository;
import com.Sambhav.FoodOrdering.Repository.RestaurantRepository;
import com.Sambhav.FoodOrdering.Repository.UserRepository;
import com.Sambhav.FoodOrdering.model.Address;
import com.Sambhav.FoodOrdering.model.Restaurant;
import com.Sambhav.FoodOrdering.model.Users;
import com.Sambhav.FoodOrdering.request.CreateRestaurantRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class RestaurantServiceImpl implements RestaurantService
{
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, Users users)
    {
        Address address;
            address = addressRepository.save(request.getAddress());
//        Address address = addressRepository.findById(request.getAddress().getId()).orElseThrow(() -> new RuntimeException("Address not found with ID: " + request.getAddress().getId()));
//            if (address.getId() != 0) {
//                System.out.println("Warning: ID should not be set manually if auto-generated");
//            }


        Restaurant restaurant = new Restaurant();

        restaurant.setContactInformation(request.getContactInformation());
        restaurant.setCuisineType(request.getCuisineType());
        restaurant.setDescription(request.getDescription());
        restaurant.setImages(request.getImages());
        restaurant.setName(request.getName());
        restaurant.setOpeningHours(request.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(users);
        restaurant.setAddress(address);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception
    {
        Restaurant restaurant =findRestaurantById(restaurantId);
        if(restaurant.getCuisineType() !=null)
        {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null)
        {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if(restaurant.getName()!=null)
        {
            restaurant.setName(updatedRestaurant.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception
    {
        Restaurant restaurant =findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant()
    {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword)
    {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception
    {
        Optional<Restaurant>opt = restaurantRepository.findById(restaurantId);
        if (opt.isEmpty())
        {
            throw new Exception("Restaurant Not Find By Id"+restaurantId);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception
    {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant == null)
        {
            throw new Exception("Restaurant Not Found By OwnerId"+userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addFavourites(Long RestaurantId, Users users) throws Exception {
        Restaurant restaurant= findRestaurantById(RestaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(RestaurantId);

        if(users.getFavourites().contains(restaurantDto))
        {
            users.getFavourites().remove(restaurantDto);
        }
        else
        {
            users.getFavourites().add(restaurantDto);
        }
        userRepository.save(users);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception
    {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
