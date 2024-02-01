package com.global.system.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.system.Dao.UserDao;
import com.global.system.Exceptions.MyException;
import com.global.system.Repository.Userrepository;
import com.global.system.model.UserModel;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    Userrepository userrepository;

    public int regUser(UserModel userModel) throws MyException, SQLException {

        String email = userModel.getEmailId();
        int i = 0;
        List<UserModel> emails = userrepository.findByEmailId();

        if (emails == null || emails.isEmpty()) {

            i = insertUser(userModel);

        } else {

            boolean b = emails
                    .stream()
                    .filter(s -> s.getEmailId().equalsIgnoreCase(email))
                    .collect(Collectors.toList())
                    .isEmpty()
                            ? true
                            : false;

            i = b
                    ? (i = insertUser(userModel))
                    : 10;

        }
        if (i != -1) {
            return i;
        } else {
            
            throw new MyException("user register not success");
        }

    }

    public int insertUser(UserModel userModel) {
        UserModel um = userrepository.save(userModel);
        int i = (um == null) ? -1 : 1;
        return i;

    }
}
