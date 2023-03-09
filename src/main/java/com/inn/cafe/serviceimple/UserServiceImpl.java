package com.inn.cafe.serviceimple;

import com.inn.cafe.Constant;
import com.inn.cafe.dao.UserDAO;
import com.inn.cafe.jwt.CustomerUsersDetailsService;
import com.inn.cafe.model.User;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;


    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp : ", requestMap);

        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDAO.findByEmailId(requestMap.get(User.UserType.email));
                if (Objects.isNull(user)) {
                    user = User.getUserFromMap(requestMap);
                    userDAO.save(user);
                    return Utility.getResponseEntity(Constant.MSG_SUCCESS_REGISTRATION, HttpStatus.BAD_REQUEST);
                } else {
                    return Utility.getResponseEntity(Constant.MSG_EMAIL_EXISTS, HttpStatus.OK);
                }
            } else {
                return Utility.getResponseEntity(Constant.MSG_INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Utility.getResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login : " + requestMap.toString());

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestMap.get("email"),
                            requestMap.get("password")));
            if(auth.isAuthenticated()){
                if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    //return new ResponseEntity<String>()
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey(User.UserType.name.name()) && requestMap.containsKey(User.UserType.contact_number.name())
                && requestMap.containsKey(User.UserType.email.name()) && requestMap.containsKey(User.UserType.password.name());
    }

}
