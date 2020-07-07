package com.itbooking;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itbooking.pojo.User;
import com.itbooking.service.IUserService;
import com.itbooking.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TmallManyDatasourceOneApplicationTests {

	//@Autowired
    //private UserService userService;
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
