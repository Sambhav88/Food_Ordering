package com.Sambhav.FoodOrdering.Controller;

import com.Sambhav.FoodOrdering.Service.UserService;
import com.Sambhav.FoodOrdering.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<Users> findUserbyJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        Users users = userService.findByJwtToken(jwt);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
