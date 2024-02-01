package com.global.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.system.Interfaces.LoginInterface;
import com.global.system.Repository.Userrepository;
import com.global.system.model.UserModel;

@Service
public class LoginService {

    @Autowired
    Userrepository userrepository;

    public UserModel userLogin(String email, String password) {
         
     
		LoginInterface loginInterface = (useremail, userpassword) -> {
		
			UserModel rs=null;
			try {
				rs = userrepository.findByEmailIdAndPassword(useremail,userpassword);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return rs;

		};
 
		UserModel um = loginInterface.userLogin(email, password);
		return um;

	}


	public UserModel getProfile(String userid){
      
		UserModel userModel=userrepository.findById(Integer.parseInt(userid)).get();
		return userModel;
	}
    
} 
     










































