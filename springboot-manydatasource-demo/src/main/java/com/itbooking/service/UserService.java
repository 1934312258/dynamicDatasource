package com.itbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itbooking.core.DS;
import com.itbooking.mapper.UserMapper;
import com.itbooking.pojo.User;

@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;

    @DS("datasource2")
    public int insert(User pojo) {
        return userMapper.insert(pojo);
    }

    @DS("datasource1")
    public int insertList(List<User> pojos) {
        return userMapper.insertList(pojos);
    }

    @DS("datasource1")
    public List<User> select(User pojo) {
        return userMapper.select(pojo);
    }

    @DS("datasource1")
	public List<User> findAll() {
		return userMapper.findAll();
	}

    @DS("datasource1")
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @DS("datasource1")
    public int update(User pojo) {
        return userMapper.update(pojo);
    }

}
