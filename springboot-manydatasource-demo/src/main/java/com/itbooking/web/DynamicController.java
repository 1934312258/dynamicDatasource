package com.itbooking.web;

import com.itbooking.pojo.User;
import com.itbooking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 徐柯老师
 * @Description
 * @Tel/微信：15074816437
 * @Version 1.0
 **/
@RestController
@RequestMapping("/dynamic")
public class DynamicController {


    @Autowired
    private IUserService userService;

    // todo :http://localhost:9007/dynamic/find
    @GetMapping("/find")
    public List<User> test(){
        return userService.findAll();
    }

}
