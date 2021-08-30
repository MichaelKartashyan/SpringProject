package com.example.springproject.services;

import com.example.springproject.dto.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServices extends UserDetailsService {

    Users updateUser(Users user);
    Users getUser(String email);
    Users addUser(Users user);
    Boolean isFreeEmail(String email);
}
