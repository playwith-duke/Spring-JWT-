package com.example.demo.repository;

import com.example.demo.bean.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<RegisterUser,Integer> {
    RegisterUser findByUsername(String username);
}
