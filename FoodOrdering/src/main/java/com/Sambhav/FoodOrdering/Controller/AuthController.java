package com.Sambhav.FoodOrdering.Controller;

import com.Sambhav.FoodOrdering.Config.JwtProvider;
import com.Sambhav.FoodOrdering.Repository.CartRepositry;
import com.Sambhav.FoodOrdering.Repository.UserRepository;
import com.Sambhav.FoodOrdering.Service.CustomUserDetailsService;
import com.Sambhav.FoodOrdering.model.Cart;
import com.Sambhav.FoodOrdering.model.User_Role;
import com.Sambhav.FoodOrdering.model.Users;
import com.Sambhav.FoodOrdering.request.LoginRequest;
import com.Sambhav.FoodOrdering.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CartRepositry cartRepositry;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody Users users) throws Exception {
        Users isEmailExist=userRepository.findByEmail(users.getEmail());
        if(isEmailExist!=null)
        {
            throw new Exception("Email Already Used with another account ");
        }
        Users createdUser = new Users();
        createdUser.setEmail(users.getEmail());
        createdUser.setFullname(users.getFullname());
        createdUser.setRole(users.getRole());
        createdUser.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = userRepository.save(createdUser);

        Cart cart=new Cart();
        cart.setCustomer(savedUser);
        cartRepositry.save(cart);

        Authentication authentication =new UsernamePasswordAuthenticationToken(users.getEmail(),users.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);
        AuthResponse authResponse =new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register Success");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/Sign")
    public ResponseEntity<AuthResponse>signin(@RequestBody LoginRequest req)
    {
        String username = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(username,password);

        Collection< ? extends GrantedAuthority>authorities=authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt=jwtProvider.generateToken(authentication);

        AuthResponse authResponse =new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register Success");
        authResponse.setRole(User_Role.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    private Authentication authenticate(String username,String password)
    {
        UserDetails userDetails =customUserDetailsService.loadUserByUsername(username);
        if(userDetails == null)
        {
            throw new BadCredentialsException("Invalid Username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid Password ");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
