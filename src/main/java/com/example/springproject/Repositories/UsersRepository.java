package com.example.springproject.Repositories;

import com.example.springproject.dto.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);
}
