package com.inn.cafe.dao;

import com.inn.cafe.entity.User;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDAO extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email")
    User findByEmailId(@Param("email") String email);

    @Query("select new com.inn.cafe.wrapper.UserWrapper(u.id, u.name, u.email, u.contactNumber, u.status) from User as u where u.role='user'")
    List<UserWrapper> getAllUsers();

    @Transactional
    @Modifying
    @Query("update User u set u.status = :status where u.id = :id")
    int updateStatus(String status, @Param("id") int id);

}
