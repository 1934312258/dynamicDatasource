package com.itbooking.service;

import com.itbooking.mapper.UserDao;
import com.itbooking.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;


    public int insert(User pojo) {
        return userDao.insert(pojo);
    }

    public int insertList(List<User> pojos) {
        return userDao.insertList(pojos);
    }

    public List<User> select(User pojo) {
        return userDao.select(pojo);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public int update(User pojo) {
        return userDao.update(pojo);
    }

}
