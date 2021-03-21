package com.example.demo.controller;

import com.example.demo.DisplayUser;
import com.example.demo.bean.RegisterUser;
import com.example.demo.bean.User;
import com.example.demo.bean.UserRole;
import com.example.demo.bean.authentication.AuthenticationRequest;
import com.example.demo.exception.exceptions.InvalidUserCredentials;
import com.example.demo.exception.exceptions.UserAlreadyExistsException;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.RegisterRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class MyController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/api/users")
    public List<DisplayUser> displayUsers(){
        List<User> users=userRepository.findAll();
        List<DisplayUser> displayUsers=new ArrayList<>();
        for(User user:users){
            DisplayUser display=new DisplayUser(user.getId(),user.getUsername(),user.getUserRoles());
            displayUsers.add(display);
        }
        return displayUsers;
    }

    @PostMapping("/api/auth")
    public String authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (Exception e) {
            throw new InvalidUserCredentials("Username or Password wrong");
        }
        return jwtUtil.generateToken(authenticationRequest.getUsername());
    }

    @PostMapping("/api/register")
    public String addUser(@RequestBody RegisterUser registerUser) throws Exception {
        RegisterUser user= registerRepository.findByUsername(registerUser.getUsername());
        if(user!=null){
            throw new UserAlreadyExistsException("User Already Exists...");
        }
        try {
            String password=bCryptPasswordEncoder.encode(registerUser.getPassword());
            registerUser.setPassword(password);
            registerRepository.save(registerUser);
            setUserDetails(registerUser);
        }catch (Exception e){
            throw new Exception("Unknown Internal Error");
        }
        return "Successfully added the user";
    }

    private List<UserRole> setUserRoles(){
        UserRole role=new UserRole();
        role.setUserrole("USER");
        List<UserRole> userRoles=new ArrayList<>();
        userRoles.add(role);
        return userRoles;
    }
    private void setUserDetails(RegisterUser registerUser){
        User setUser=new User();
        List<UserRole> roles=setUserRoles();
        setUser.setUserRoles(roles);
        setUser.setId(registerUser.getId());
        setUser.setUsername(registerUser.getUsername());
        setUser.setPassword(registerUser.getPassword());
        userRepository.save(setUser);
    }

    @PostConstruct
    public void init(){
        RegisterUser registerUser=new RegisterUser();
        registerUser.setId(1);
        registerUser.setFirstname("Siva");
        registerUser.setLastname("Sanker");
        registerUser.setUsername("siva");
        registerUser.setPassword(bCryptPasswordEncoder.encode("123456"));
        registerRepository.save(registerUser);
        setUserDetails(registerUser);
    }
}
