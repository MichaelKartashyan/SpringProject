package com.example.springproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name= "title")
    private String title;

    @Column(name= "short_content")
    private String shortContent;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "picture_url")
    private String pictureURL;

    @Column(name = "post_date", columnDefinition = "timestamp")
    private Timestamp postDate;

    @ManyToMany(fetch = FetchType.LAZY)
    List<CategoriesPost> categoriesPost;
}




