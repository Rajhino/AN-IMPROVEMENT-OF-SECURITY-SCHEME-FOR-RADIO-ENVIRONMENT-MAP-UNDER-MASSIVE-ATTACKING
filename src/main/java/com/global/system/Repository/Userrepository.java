package com.global.system.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.global.system.model.UserModel;

public interface Userrepository extends JpaRepository<UserModel, Integer> {

    @Query(value = "select * from user_model", nativeQuery = true)
    List<UserModel> findByEmailId();

    UserModel findByEmailIdAndPassword(String useremail, String userpassword);

}
                                                                                                                             