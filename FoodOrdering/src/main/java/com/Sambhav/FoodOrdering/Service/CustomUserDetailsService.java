package com.Sambhav.FoodOrdering.Service;

import com.Sambhav.FoodOrdering.Repository.UserRepository;
import com.Sambhav.FoodOrdering.model.User_Role;
import com.Sambhav.FoodOrdering.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Users user = userRepository.findByEmail(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        User_Role role = user.getRole();
//        if(role == null)
//        {
//            role = User_Role.ROLE_CUSTOMER;
//        }
        List<GrantedAuthority> authorioty = new ArrayList<>();
        authorioty.add(new SimpleGrantedAuthority(role.toString()));

        return new User(user.getEmail(),user.getPassword(),authorioty);
    }
}
