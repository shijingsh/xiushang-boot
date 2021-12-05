package com.xiushang.service.impl;

import com.xiushang.common.user.service.UserService;
import com.xiushang.entity.UserEntity;
import com.xiushang.jpa.repository.UserDao;
import com.xiushang.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.findByLoginName(username);
        if(user == null){
            throw new UsernameNotFoundException(username + " 用户不存在");
        }
        return new SecurityUser(user);
    }

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userDao.findById(userId);
        if(!optional.isPresent()){
            throw new UsernameNotFoundException(userId);
        }
        UserEntity user = optional.get();
        return new SecurityUser(user);
    }

    public UserDetails loadUserByOpenId(String openId) {
        UserEntity user = userService.getUserByUnionId(openId);
        if(user == null){
           return null;
        }

        return new SecurityUser(user);
    }
}
