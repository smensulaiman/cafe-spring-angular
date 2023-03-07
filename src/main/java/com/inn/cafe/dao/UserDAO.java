package com.inn.cafe.dao;

import com.inn.cafe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email")
    User findByEmailId(@Param("email") String email);

}
