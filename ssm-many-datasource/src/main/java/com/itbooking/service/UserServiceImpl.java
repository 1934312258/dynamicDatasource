package com.itbooking.service;

import com.itbooking.mapper.read.UserReadDao;
import com.itbooking.mapper.write.UserWriteDao;
import com.itbooking.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserReadDao userReadDao;

    @Autowired
    private UserWriteDao userWriteDao;

    public int insert(User pojo) {
        return userWriteDao.insert(pojo);
    }

    public int insertList(List<User> pojos) {
        return userWriteDao.insertList(pojos);
    }

    public List<User> select(User pojo) {
        return userReadDao.select(pojo);
    }

    public List<User> findAll() {
        return userReadDao.findAll();
    }

    public User findById(Long id) {
        return userReadDao.findById(id);
    }

    public int update(User pojo) {
        return userWriteDao.update(pojo);
    }

}
