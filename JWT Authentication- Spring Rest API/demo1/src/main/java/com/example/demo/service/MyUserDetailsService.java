package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user= repository.findByUsername(username);
       if(user==null)
           throw new UsernameNotFoundException("User Not Found");
        Collection<? extends GrantedAuthority> roles=
                user.getUserRoles()
                        .stream()
                        .map(role->role.getUserrole())
                        .map(role->new SimpleGrantedAuthority("ROLE_"+role))
                        .collect(Collectors.toList());
       return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),roles);
    }
}
