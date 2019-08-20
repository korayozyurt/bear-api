package com.mkt.uzaktanelemanapi.controller;

import com.mkt.uzaktanelemanapi.entity.BearUser;
import com.mkt.uzaktanelemanapi.service.UserService;
import com.mkt.uzaktanelemanapi.tools.BEAR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(BEAR.version + "/user")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public List<BearUser> getUserList() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserbyId(@PathVariable("userId") String userId){
        return userService.get(userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BearUser> addUser(@RequestBody BearUser bearUser) {
        bearUser.setPassword(bCryptPasswordEncoder.encode(bearUser.getPassword()));
        return userService.save(bearUser);
    }

    @PutMapping("/update-description/{userId}")
    public int updateDescription(@PathVariable("userId") String id,
                                                    @RequestBody String description) {
        return userService.updateDescription(id,description);
    }

    @PutMapping("/update-name-surname")
    public int updateNameSurname(@RequestBody BearUser bearUser) {
        return userService.updateNameUsername(bearUser);
    }

    @PutMapping("/update-password")
    public ResponseEntity changePassword(@RequestBody String json)  {
        return userService.changePassword(json);
    }

    @PutMapping("/update-email")
    public ResponseEntity updateEmail(@RequestBody String json) {
        return userService.updateEmail(json);
    }

    @PutMapping("/update-communication-info")
    public ResponseEntity updateCommunicationInfo(@RequestBody String json) {
        return userService.updateCommunicationInfo(json);
    }

    @PutMapping("/update-avatar")
    public ResponseEntity updateAvatar(@RequestBody String json) {
        return userService.updateAvatar(json);
    }

}
