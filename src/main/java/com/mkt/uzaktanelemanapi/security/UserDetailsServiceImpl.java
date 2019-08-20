package com.mkt.uzaktanelemanapi.security;

import com.mkt.uzaktanelemanapi.entity.BearUser;
import com.mkt.uzaktanelemanapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userInfo) throws UsernameNotFoundException {
        BearUser bearUser = userService.findByemailOrUsername(userInfo);
        if(bearUser == null) {
            throw new UsernameNotFoundException(userInfo);
        }
        return new org.springframework.security.core.userdetails.User(
                bearUser.getId(),
                bearUser.getPassword(),
                Collections.emptyList()
        );
    }
}
