package com.itbooking.mapper;

import com.itbooking.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int insert(@Param("pojo") User pojo);

    int insertList(@Param("pojos") List<User> pojo);

    List<User> select(@Param("pojo") User pojo);

    List<User> findAll();

    User findById(@Param("id") Long id);

    int update(@Param("pojo") User pojo);

    int deleteById(@Param("id") Long id);

}
