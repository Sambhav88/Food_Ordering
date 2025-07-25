package com.Sambhav.FoodOrdering.Service;

import com.Sambhav.FoodOrdering.Config.JwtProvider;
import com.Sambhav.FoodOrdering.Repository.UserRepository;
import com.Sambhav.FoodOrdering.model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService
{
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Users findByJwtToken(String jwt) throws Exception
    {
        String email = jwtProvider.getemailfromjwttoken(jwt);
        Users users = userRepository.findByEmail(email);
        return users;
    }

    @Override
    public Users findUserByEmail(String email) throws Exception
    {
        Users users =userRepository.findByEmail(email);
        if(users == null){
            throw new Exception("User Not Found");
        }
        return  users;
    }
}
