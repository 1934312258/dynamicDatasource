package com.itbooking.service;

import com.itbooking.pojo.User;

import java.util.List;

public interface IUserService {

    public int insert(User pojo) ;

    public int insertList(List<User> pojos);

    public List<User> select(User pojo);

    public List<User> findAll() ;

    public User findById(Long id) ;

    public int update(User pojo) ;
}
