package com.mkt.uzaktanelemanapi.service;

import com.mkt.uzaktanelemanapi.daoImplementation.UserDaoImplementation;
import com.mkt.uzaktanelemanapi.entity.BearUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IBearService<BearUser> {

    @Autowired
    private UserDaoImplementation userDaoImplementation;


    @Override
    public List<BearUser> getAll() {
        return userDaoImplementation.getAll();
    }

    @Override
    public ResponseEntity get(String id) {
        return userDaoImplementation.get(id);
    }

    @Override
    public ResponseEntity<BearUser> save(BearUser bearUser) {
        return userDaoImplementation.save(bearUser);
    }

    @Override
    public void delete() {

    }

    public BearUser getUser(String userId){
        return userDaoImplementation.getUser(userId);
    }

    public BearUser findByemailOrUsername(String userInfo) {
        return userDaoImplementation.findByEmailOrUsername(userInfo);
    }

    public int updateDescription(String id,String description) {
        return userDaoImplementation.updateDescription(id,description);
    }

    public int updateNameUsername(BearUser bearUser) {
        return userDaoImplementation.updateNameSurname(bearUser);
    }

    public ResponseEntity changePassword(String json) {
        return userDaoImplementation.changePassword(json);
    }

    public ResponseEntity updateEmail(String json) {
        return userDaoImplementation.updateEmail(json);
    }

    public ResponseEntity updateCommunicationInfo(String json) {
        return userDaoImplementation.updateCommunicationInfo(json);
    }

    public ResponseEntity updateAvatar(String json) {
        return userDaoImplementation.updateAvatar(json);
    }

}






































