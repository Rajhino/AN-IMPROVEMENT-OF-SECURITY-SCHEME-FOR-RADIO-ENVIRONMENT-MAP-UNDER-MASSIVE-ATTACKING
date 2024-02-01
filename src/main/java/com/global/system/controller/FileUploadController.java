package com.global.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.global.system.Repository.FileRepository;
import com.global.system.model.FileModel;
import com.global.system.service.FileUploadService;
import com.global.system.utils.ResponseMessage;
import com.global.system.utils.ResponseMessageForFile;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    FileRepository fileRepository;

    @PostMapping("/fileUpload")
    public ResponseEntity<ResponseMessage> fileUpload(@RequestParam("file") byte[] file,
    @RequestParam("userid") String userid,@RequestParam("fileName") String fileName
    ){
      long start = System.currentTimeMillis();

        try{
        
          CustomMultipartFile customMultipartFile = new CustomMultipartFile(file, fileName);
          try {
          customMultipartFile.transferTo(customMultipartFile.getFile());
          
          } catch (IllegalStateException e) {
            //  log.info("IllegalStateException : " + e);
          } catch (IOException e) {
           //   log.info("IOException : " + e);
          }
          long end = System.currentTimeMillis();
      FileModel fm1=  fileUploadService.save(customMultipartFile,Integer.parseInt(userid));
       String message = "success";
       ResponseMessage responseMessage=new ResponseMessage(message);
       responseMessage.setFileModel(fm1);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }catch (Exception e) {
           String message = "failed";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
          }

    }

    @GetMapping("/files")
    public ResponseEntity<List<FileModel>> getListFiles() {
     // List<FileModel> filess=fileRepository.findByUserId(22);
      List<FileModel> fileInfos = fileUploadService.loadAll().map(path -> {
      
        String filename = path.getFileName().toString();
        
        String url = MvcUriComponentsBuilder
            .fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build().toString();

        return new FileModel(filename, url);
      }).collect(Collectors.toList());
  
     // List<String> first=filess.stream().map(p->p.getFileName()).collect(Collectors.toList());
     
      return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
      Resource file = fileUploadService.load(filename);
      System.out.println("resource fo the file"+file);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping("/userFiles/{userId}")
    public ResponseMessageForFile getListFiles(@PathVariable String userId){
       
      List<FileModel> filess=fileRepository.findByuserId(Integer.parseInt(userId));
      List<FileModel> fileInfos=new ArrayList();
      fileInfos= filess.stream().map(path -> {
      
        String filename = path.getFileName().toString();
        
        String url = MvcUriComponentsBuilder
            .fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build().toString();

        return new FileModel(filename, url);
      }).collect(Collectors.toList());        

      ResponseMessageForFile rmf=new ResponseMessageForFile();
      rmf.setMessage("success");
      rmf.setFileModel(fileInfos);
      return rmf;
    }


    @GetMapping("/userHarmFiles/{userId}")
    public ResponseMessageForFile getHarmListFiles(@PathVariable String userId){
       
      List<FileModel> filess=fileRepository.findByuserId(Integer.parseInt(userId));
      List<FileModel> fileInfos=new ArrayList();
      fileInfos= filess.stream().map(path -> {
      
        String filename = path.getFileName().toString();
        
        String url = MvcUriComponentsBuilder
            .fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build().toString();
         int status=path.getStatus();
         int fileId=path.getFileId();
        return new FileModel(filename, url,status,fileId);
      }).collect(Collectors.toList());        

      ResponseMessageForFile rmf=new ResponseMessageForFile();
      rmf.setMessage("success");
     fileInfos= fileInfos.stream().filter(s->s.getStatus()==1).collect(Collectors.toList());
      rmf.setFileModel(fileInfos);
      return rmf;
    }
  
    @RequestMapping("/deleteFile/{fileId}")
    public String deleteFile(@PathVariable String fileId){

      FileModel fm=fileRepository.getReferenceById(Integer.parseInt(fileId));
        fileRepository.delete(fm);
        return "success";
      

    }
    
}
