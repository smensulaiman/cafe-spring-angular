package com.inn.cafe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    public enum UserType{
        id,
        name,
        contact_number,
        email,
        password,
        status,
        role
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    public static User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get(UserType.name.name()));
        user.setContactNumber(requestMap.get(UserType.contact_number.name()));
        user.setEmail(requestMap.get(UserType.email.name()));
        user.setPassword(requestMap.get(UserType.password.name()));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

}
