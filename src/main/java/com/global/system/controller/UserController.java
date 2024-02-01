package com.global.system.controller;

import java.sql.SQLException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.system.Exceptions.MyException;
import com.global.system.model.UserModel;
import com.global.system.service.LoginService;
import com.global.system.service.UserService;
import com.global.system.utils.ResponseMessage;


@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:8081")
public class UserController { 

    @Autowired UserService userService;
    @Autowired ResponseMessage responseMessage;
    @Autowired
    LoginService loginService;

   

    @PostMapping("/Register")
    public ResponseMessage registerUser(@RequestBody UserModel userModel)throws MyException, SQLException {

        int i = userService.regUser(userModel);
        if (i != -1) {
            if (i != 10) {
                responseMessage.setMessage("successs");
            } else {
                responseMessage.setMessage("email");
            }
        } else {
            responseMessage.setMessage("failed");

        }
        return responseMessage;
    }

    @PostMapping("/login")
    public ResponseMessage checkLogin(@RequestBody UserModel userModel){
        UserModel um = loginService.userLogin(userModel.getEmailId(), userModel.getPassword());
        ResponseMessage rm=new ResponseMessage();
          
         if(um!=null){
            rm.setMessage("success");
            rm.setUserModel(um);
            
         }else{
            rm.setMessage("failed");
            rm.setUserModel(um);
         }
         return rm;
    }

    @GetMapping("/profile/{userid}")
    public ResponseMessage checkLogin(@PathVariable String  userid){
      UserModel um=loginService.getProfile(userid);
      if(um!=null){
        ResponseMessage rm=new ResponseMessage();
        rm.setMessage("success");
        rm.setUserModel(um);
        return rm;
      }else{
        ResponseMessage rm=new ResponseMessage();
        rm.setMessage("failed");
        return rm;
      }
    }

}
