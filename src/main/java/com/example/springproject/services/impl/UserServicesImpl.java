package com.example.springproject.services.impl;

import com.example.springproject.Repositories.UsersRepository;
import com.example.springproject.dto.Users;
import com.example.springproject.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(s);
        if(user!=null) return user;
        else throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users addUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean isFreeEmail(String email) {
        Users u = userRepository.findByEmail(email);
        if(u!=null){
            return false;
        }
        return true;
    }
}
