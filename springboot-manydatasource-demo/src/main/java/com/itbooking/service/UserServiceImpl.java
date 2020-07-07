package com.itbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbooking.core.DS;
import com.itbooking.mapper.UserMapper;
import com.itbooking.pojo.User;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMappper;

    public int insert(User pojo) {
        return userMappper.insert(pojo);
    }

    public int insertList(List<User> pojos) {
        return userMappper.insertList(pojos);
    }

    public List<User> select(User pojo) {
        return userMappper.select(pojo);
    }

    public List<User> findAll() {
        return userMappper.findAll();
    }

    public User findById(Long id) {
        return userMappper.findById(id);
    }

    public int update(User pojo) {
        return userMappper.update(pojo);
    }

}
