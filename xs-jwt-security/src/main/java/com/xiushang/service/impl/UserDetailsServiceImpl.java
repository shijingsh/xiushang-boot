package com.xiushang.service.impl;

import com.xiushang.entity.UserEntity;
import com.xiushang.jpa.repository.UserDao;
import com.xiushang.security.SecurityUser;
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

    private UserDao userDao;

    /**
     * 通过构造器注入userDao
     * @param userDao
     */
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

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
}
