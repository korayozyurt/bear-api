package com.mkt.uzaktanelemanapi.controller;

import com.mkt.uzaktanelemanapi.service.StorageService;
import com.mkt.uzaktanelemanapi.tools.BEAR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequestMapping(BEAR.version + "/file")
public class FileController {

    @Autowired
    private StorageService storageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file")MultipartFile file, @RequestHeader("username") String username) {
        String fileName = UUID.randomUUID().toString() + "---" + file.getOriginalFilename();
        return storageService.store(file,fileName, username);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename){
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/{username}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFileByUserName(@PathVariable("filename") String filename, @PathVariable("username") String username){
        Resource file = storageService.loadFile(username + "/" + filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}










































