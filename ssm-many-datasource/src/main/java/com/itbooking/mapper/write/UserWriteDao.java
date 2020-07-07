package com.itbooking.mapper.write;

import com.itbooking.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserWriteDao {

    int insert(@Param("pojo") User pojo);

    int insertList(@Param("pojos") List<User> pojo);

    int update(@Param("pojo") User pojo);

    int deleteById(@Param("id") Long id);

}
