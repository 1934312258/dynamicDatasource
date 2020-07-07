package com.itbooking.mapper.read;

import com.itbooking.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserReadDao {


    List<User> select(@Param("pojo") User pojo);

    List<User> findAll();

    User findById(@Param("id") Long id);


}
