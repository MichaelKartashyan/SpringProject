//package com.example.springproject.beans;
//
//
//import com.example.springproject.dto.Post;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//
//@Data
//@Component
//public class PostsBean implements BeanInterfase{
//    private static Connection connection;
//
//
////    public PostsBean(){
////
////            try{
////                Class.forName("com.mysql.cj.jdbc.Driver");
////                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_lessons?serverTimezone=UTC","root","");
////            }catch (Exception e){
////                e.printStackTrace();
////            }
////    }
//
//
//    public void addPost(Post post) {
//        try{
//
//            PreparedStatement statement = connection.prepareStatement("" +
//                    "INSERT INTO posts (title,short_content,content,picture_url,post_date) " +
//                    "VALUES (?, ?, ?, ?, ?)");
//
//            statement.setString(1, post.getTitle());
//            statement.setString(2, post.getShortContent());
//            statement.setString(3, post.getContent());
//            statement.setString(4, post.getPictureURL());
//            statement.setTimestamp(5, post.getPostDate());
//
//            int rows = statement.executeUpdate();
//            statement.close();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    public ArrayList<Post> getAllPosts() {
//
//        ArrayList<Post> posts = new ArrayList<>();
//        try{
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts");
//            ResultSet resultSet = statement.executeQuery();
//
//            while(resultSet.next()){
//
//                posts.add(
//                        new Post(
//                                resultSet.getLong("id"),
//                                resultSet.getString("title"),
//                                resultSet.getString("short_content"),
//                                resultSet.getString("content"),
//                                resultSet.getString("picture_url"),
//                                resultSet.getTimestamp("post_date")
//                                )
//                );
//
//
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return posts;
//
//    }
//
//    public Post getPost(Long id) {
//
//        Post post = null;
//
//        try{
//
//            PreparedStatement statement = connection.prepareStatement("" +
//                    "SELECT * " +
//                    "FROM posts " +
//                    "WHERE id = ? LIMIT 1");
//
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//
//            if(resultSet.next()){
//
//                post = new Post(
//                                resultSet.getLong("id"),
//                                resultSet.getString("title"),
//                                resultSet.getString("short_content"),
//                                resultSet.getString("content"),
//                                resultSet.getString("picture_url"),
//                                resultSet.getTimestamp("post_date")
//                );
//
//            }
//            statement.close();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return post;
//
//    }
//
//
//    public void deletePost(Long id){
//        try{
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM posts WHERE id = ? ");
//
//            statement.setLong(1, id);
//             statement.executeUpdate();
//
//            statement.close();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void updatePost(Post post) {
//        try {
//            PreparedStatement statement = connection.prepareStatement("UPDATE posts " +
//                    "SET  title=?, short_content=?, content=?, picture_url=? " +
//                    "WHERE id=?");
//
//
//            statement.setString(1, post.getTitle());
//            statement.setString(2, post.getShortContent());
//            statement.setString(3, post.getContent());
//            statement.setString(4, post.getPictureURL());
//            statement.setLong(5, post.getId());
//
//             statement.executeUpdate();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
