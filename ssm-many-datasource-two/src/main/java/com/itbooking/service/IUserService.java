package com.itbooking.service;

import com.itbooking.core.DataSource;
import com.itbooking.core.DynamicDataSourceGlobal;
import com.itbooking.pojo.User;

import java.util.List;

public interface IUserService {

    @DataSource(DynamicDataSourceGlobal.WRITE)
    public int insert(User pojo) ;

    @DataSource(DynamicDataSourceGlobal.WRITE)
    public int insertList(List<User> pojos);

    @DataSource
    public List<User> select(User pojo);

    @DataSource(DynamicDataSourceGlobal.READ)
    public List<User> findAll() ;

    @DataSource(DynamicDataSourceGlobal.READ)
    public User findById(Long id) ;

    @DataSource(DynamicDataSourceGlobal.WRITE)
    public int update(User pojo) ;
}
