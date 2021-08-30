package com.example.springproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesPost {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

   @Column(name = "category_name")
    private String categoryName;

}
