package com.inn.cafe.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWrapper {

    private int id;
    private String name;
    private String email;
    private String contactNumber;
    private String status;

}
