package com.example.springproject.Repositories;

import com.example.springproject.dto.CategoriesPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface CategoryPostRepositories extends JpaRepository<CategoriesPost, Long> {


}
