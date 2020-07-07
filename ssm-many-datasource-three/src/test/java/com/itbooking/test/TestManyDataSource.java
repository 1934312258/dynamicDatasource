package com.itbooking.test;

import com.itbooking.mapper.UserDao;
import com.itbooking.pojo.User;
import com.itbooking.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author 徐柯老师
 * @Description
 * @Tel/微信：15074816437
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
public class TestManyDataSource {

    @Autowired
    private IUserService userService;

    @Test
    public void find(){
        List<User> users = userService.findAll();
        for (User user: users){
            System.out.println(user);
        }
    }

    @Test
    public void save(){
        User user = new User();
        user.setName("zhangsan");
        user.setPassword("12345678");
        user.setUsername("张三");
        userService.insert(user);
    }

}
