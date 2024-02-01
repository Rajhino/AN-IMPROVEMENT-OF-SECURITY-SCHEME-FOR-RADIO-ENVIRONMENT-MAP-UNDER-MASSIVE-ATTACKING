package com.global.system.utils;

import java.util.List;

import com.global.system.model.EndpointsModel;
import com.global.system.model.FileModel;
import com.global.system.model.UserModel;

public class AdminResponse {

    private String message;
    private List<UserModel> userModels;
    private List<FileModel> fileModels;
    private List<EndpointsModel> endpointsModels;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<UserModel> getUserModels() {
        return userModels;
    }
    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
    }
    public List<FileModel> getFileModels() {
        return fileModels;
    }
    public void setFileModels(List<FileModel> fileModels) {
        this.fileModels = fileModels;
    }
    public List<EndpointsModel> getEndpointsModels() {
        return endpointsModels;
    }
    public void setEndpointsModels(List<EndpointsModel> endpointsModels) {
        this.endpointsModels = endpointsModels;
    }


    
    
}
