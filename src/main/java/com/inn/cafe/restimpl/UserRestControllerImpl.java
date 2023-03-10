package com.inn.cafe.restimpl;

import com.inn.cafe.Constant;
import com.inn.cafe.restcontroller.UserRestController;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.Utility;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestControllerImpl implements UserRestController {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
       try {
        return userService.signUp(requestMap);
       }catch (Exception e){
           e.printStackTrace();
       }
        return Utility.getResponseEntity(Constant.INSTANCE.MSG_SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Utility.getResponseEntity(Constant.INSTANCE.MSG_SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {

            return userService.getALlUSers();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        return userService.update(requestMap);
    }
}
