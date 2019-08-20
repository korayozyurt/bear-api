package com.mkt.uzaktanelemanapi.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    private final Path rootLocation = Paths.get("upload-dir");
    private final String OFFLINE_USER_PATH = "offline_users";

    public ResponseEntity store(MultipartFile file,String fileName, String username){
        String subPath = "";
        String creatingDir = "";
        try{
            if (username.equals("")) {
                creatingDir = OFFLINE_USER_PATH;
                subPath = OFFLINE_USER_PATH + "/" + fileName;
            } else {
                creatingDir = username;
                subPath = username + "/" + fileName;
            }
            if (!createDirectory(creatingDir)) {
                return new ResponseEntity("Dosya yolu oluşturulamadı", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Files.copy(file.getInputStream(),this.rootLocation.resolve(subPath));

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(subPath ,HttpStatus.CREATED);
    }

    private boolean createDirectory(String directory) {
        File dir = new File(rootLocation + "/" + directory);
        if(! dir.exists()) {
            return dir.mkdir();
        }
        return true;
    }

    public Resource loadFile(String filename){
        try{
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else{
                throw new RuntimeException("FAILED to read file");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to read file");
        }
    }

    public ResponseEntity deleteFile(String file) {
        try {
            Files.deleteIfExists(Paths.get(rootLocation + "/" + file));
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public void deleteAll(){
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init(){
        try {
            Files.createDirectory(rootLocation);
        } catch (FileAlreadyExistsException e){
            //do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
