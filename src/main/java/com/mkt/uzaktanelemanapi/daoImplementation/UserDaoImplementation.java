package com.mkt.uzaktanelemanapi.daoImplementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkt.uzaktanelemanapi.dao.IDao;
import com.mkt.uzaktanelemanapi.entity.BearUser;
import com.mkt.uzaktanelemanapi.repository.UserRepository;
import com.mkt.uzaktanelemanapi.tools.BEAR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class UserDaoImplementation implements IDao<BearUser> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity get(String id) {
        BearUser bearUser = userRepository.findById(id).orElse(null);
        if(bearUser == null) {
            return BEAR.notAcceptableErrorResponse("bearUser cannot found");
        }
        return new ResponseEntity<BearUser>(bearUser, HttpStatus.OK);
    }


    @Override
    public List<BearUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<BearUser> save(BearUser bearUser) {
        if( userRepository.findByEmail(bearUser.getEmail()) != null) {
            return BEAR.notAcceptableErrorResponse("Bu eposta adresi ile kayıtlı bir hesap bulunmaktadır");
        }

        if(userRepository.findByUsername(bearUser.getUsername()) != null) {
            return BEAR.notAcceptableErrorResponse("bu kullanıcı ismi ile kayıtlı bir hesap bulunmaktadır");
        }

        try {
            userRepository.save(bearUser);
            return BEAR.successResponse("Kayıt Başarılı");
        } catch (DuplicateKeyException e) {
            return BEAR.notAcceptableErrorResponse(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            if(e.getCause().getCause().getMessage().contains("Duplicate entry")) {
                return BEAR.notAcceptableErrorResponse("Bu bilgilere ait başka bir hesap bulunmuştur");
            } else {
                return BEAR.notAcceptableErrorResponse(e.getMessage());
            }
        }
        catch (Exception e) {
            return BEAR.notAcceptableErrorResponse(e.getMessage());
        }
    }

    @Override
    public void delete(BearUser bearUser) {
        userRepository.delete(bearUser);
    }

    public BearUser getUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }

    public BearUser findByEmailOrUsername(String userInfo) {
        BearUser bearUser = userRepository.findByUsername(userInfo);
        if(bearUser == null ) {
            bearUser = userRepository.findByEmail(userInfo);
        }
        return bearUser;
    }

    public int updateDescription(String id, String description) {
        return userRepository.updateUserDescription(id, description);
    }

    public int updateNameSurname(BearUser bearUser) {
        BearUser existingBearUser = userRepository.findById(bearUser.getId()).orElse(null);
        if(existingBearUser != null) {
            existingBearUser.setName(bearUser.getName());
            existingBearUser.setSurname(bearUser.getSurname());
            userRepository.save(existingBearUser);
            return 1;
        }
        return -1;
    }

    public ResponseEntity changePassword(String json) {
        Map<String, Object> jsontoMap = null;
        try {
            jsontoMap = new ObjectMapper().readValue(json, Map.class);
        } catch (IOException e) {
            return new ResponseEntity("Beklenmeyen veri türü",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        BearUser bearUser = getUser((String) jsontoMap.get("userId"));
        String userCreds = checkUserCreds(bearUser, (String) jsontoMap.get("currentPassword"));
        if(userCreds == null) {
            return BEAR.serverErrorResponse(userCreds);
        }
        String newPassword = (String) jsontoMap.get("newPassword");
        bearUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(bearUser);
        return BEAR.successResponse("Şifre başarıyla değiştirildi");
    }

    public ResponseEntity updateEmail(String json) {
        Map<String, Object> jsontoMap = null;
        try {
            jsontoMap = new ObjectMapper().readValue(json, Map.class);
        } catch (IOException e) {
            return new ResponseEntity("Beklenmeyen veri türü",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(userRepository.findByEmail((String) jsontoMap.get("newEmail")) != null) {
            return BEAR.notAcceptableErrorResponse("Bu e-posta adresi ile başka bir hesap kayıtlı");
        }

        BearUser bearUser = getUser((String) jsontoMap.get("userId"));
        String userCreds = checkUserCreds(bearUser, (String) jsontoMap.get("currentPassword"));
        if(userCreds != null) {
            return BEAR.notAcceptableErrorResponse(userCreds);
        }

        bearUser.setEmail((String) jsontoMap.get("newEmail"));
        userRepository.save(bearUser);
        return BEAR.successResponse("E-posta başarıyla güncellendi");
    }

    public ResponseEntity updateCommunicationInfo(String json) {
        Map<String, Object> jsontoMap = null;
        try {
            jsontoMap = new ObjectMapper().readValue(json, Map.class);
        } catch (IOException e) {
            return new ResponseEntity("Beklenmeyen veri türü",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        BearUser bearUser = getUser((String) jsontoMap.get("userId"));
        if(bearUser == null ) {
            return BEAR.notAcceptableErrorResponse("Kullanıcı verileri işlenemedi");
        }

        bearUser.setCity((String) jsontoMap.get("city"));
        bearUser.setPhoneNumber((String) jsontoMap.get("phoneNumber"));

        userRepository.save(bearUser);
        return BEAR.successResponse("Bilgiler Başarıyla Güncellendi");

    }

    public ResponseEntity updateAvatar(String json) {
        Map<String, Object> jsontoMap = null;
        try {
            jsontoMap = new ObjectMapper().readValue(json, Map.class);
        } catch (IOException e) {
            return new ResponseEntity("Beklenmeyen veri türü",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        BearUser bearUser = getUser((String) jsontoMap.get("userId"));
        if(bearUser == null ) {
            return BEAR.notAcceptableErrorResponse("Kullanıcı verileri işlenemedi");
        }

        bearUser.setAvatarPath((String) jsontoMap.get("avatarPath"));

        return BEAR.successResponse("Profil resmi başarı ile güncellendi");
    }

    private String checkUserCreds(BearUser bearUser, String currentPassword) {
        if(bearUser == null ) {
            return "Kullanıcı verileri işlenemedi";
        }
        if (!bCryptPasswordEncoder.matches(currentPassword, bearUser.getPassword())) {
            return "Şifre doğru değil";
        }
        return null;
    }

}

































