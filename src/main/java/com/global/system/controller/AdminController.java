package com.global.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.system.service.AdminService;
import com.global.system.utils.AdminResponse;

@RestController
@RequestMapping("/admin")
public class AdminController{

    @Autowired
    AdminService adminService;
    @GetMapping("/getUsers")
    public  AdminResponse getUserDeails(){

        return adminService.getUsers();
    }

    @GetMapping("/getFiles")
    public  AdminResponse getFiles(){

        return adminService.getFiles();

    }

    @GetMapping("/getHarmFiles")
    public  AdminResponse getHarmFiles(){

        return adminService.getHarmFiles();

    }

    @GetMapping("/checkEndPoints")
    public  AdminResponse CheckEndPoints(){

        return adminService.getEndpointCheck();

    }




    
}
 