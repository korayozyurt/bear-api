package com.mkt.uzaktanelemanapi.repository;

import com.mkt.uzaktanelemanapi.entity.BearUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<BearUser,String> {
    BearUser findByEmail(String email);
    BearUser findByUsername(String username);
    BearUser findByEmailOrUsername(String email, String username);

    @Transactional
    @Modifying
    @Query("update BearUser u set u.userDescription = :description where u.id = :id")
    int updateUserDescription(@Param("id") String id, @Param("description") String description);
;}
