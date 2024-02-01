package com.global.system.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.system.Repository.EndPointRepository;
import com.global.system.Repository.FileRepository;
import com.global.system.Repository.Userrepository;
import com.global.system.model.EndpointsModel;
import com.global.system.model.FileModel;
import com.global.system.utils.AdminResponse;

@Service
public class AdminService {

    @Autowired
    Userrepository userrepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    EndPointRepository endPointRepository;

    public AdminResponse getUsers(){
      
        AdminResponse ar=new AdminResponse();
        ar.setMessage("success");
        ar.setUserModels(userrepository.findAll());
        return ar;
    }

    public AdminResponse getFiles(){
      
        AdminResponse ar=new AdminResponse();
        ar.setMessage("success");
        ar.setFileModels(fileRepository.findAll());
          List<FileModel> fileModel=ar.getFileModels();

          for(FileModel fileModel2:fileModel){
            checkVurnability(fileModel2.getFileName(),fileModel2.getFileId());
          }
        

        return ar;
    }


    public AdminResponse getHarmFiles(){
      
      AdminResponse ar=new AdminResponse();
      ar.setMessage("success");
      ar.setFileModels(fileRepository.findAll());
        List<FileModel> fileModel=ar.getFileModels();

      fileModel= fileModel.stream().filter(s -> s.getStatus()==1).collect(Collectors.toList());
      for(FileModel fm: fileModel){
        System.out.println(fm.getFileId());
      }

       /*  for(FileModel fileModel2:fileModel){
         // checkVurnability(fileModel2.getFileName(),fileModel2.getFileId());
        } */
      ar.setFileModels(fileModel);

      return ar;
  }

  public AdminResponse getEndpointCheck(){
      
    AdminResponse ar=new AdminResponse();
    ar.setMessage("success");
    ar.setEndpointsModels(endPointRepository.findAll());
      List<EndpointsModel> endPointModel= ar.getEndpointsModels();

   /*  endPointModel= endPointModel.stream().filter(s -> s.getStatus()==1).collect(Collectors.toList());
    for(FileModel fm: fileModel){
      System.out.println(fm.getFileId());
    } */

     /*  for(FileModel fileModel2:fileModel){
       // checkVurnability(fileModel2.getFileName(),fileModel2.getFileId());
      } */
  ar.setEndpointsModels(endPointModel);
    return ar;
}



    private void checkVurnability(String fileName,int fileId) {

      try                                                                                                                          
{  

  URL url = new URL("http://localhost:8081/upload/files/"+fileName);
  
  BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
  String i;
  while ((i = read.readLine()) != null){
      System.out.println("new logic"+i);
      if(i.contains("sample") || i.contains("attack") || i.contains("exe")){
       FileModel fileModel=fileRepository.getReferenceById(fileId);
       fileModel.setStatus(1);
       fileRepository.save(fileModel);

      }else if(i.contains("100") || i.contains("150") || i.contains("200")){
        FileModel fileModel=fileRepository.getReferenceById(fileId);
        fileModel.setStatus(1);
        fileRepository.save(fileModel);
      }

  }
  read.close();
/* File file=new File("http://localhost:8081/upload/files/testdoc.txt");    
FileReader fr=new FileReader(file);    
BufferedReader br=new BufferedReader(fr);  
StringBuffer sb=new StringBuffer();   
String line;  
while((line=br.readLine())!=null)  
{  
  System.out.println(line);
sb.append(line);     
sb.append("\n");     
}  
fr.close();   
System.out.println("Contents of File: ");  
System.out.println(sb.toString());  */
}    
catch(IOException e)  
{  
e.printStackTrace();  
}  


    }

    
    
}
