package com.web3.degovern.controller;

import com.web3.degovern.controller.dto.RegisterDTO;
import com.web3.degovern.controller.dto.UserDTO;
import com.web3.degovern.entity.User;
import com.web3.degovern.service.DeGovernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private DeGovernService deGovern;

    @PostMapping("register")
    public String register(@RequestBody RegisterDTO registerDTO){
        /*
        * 后面还要改*/
        deGovern.createUser(registerDTO);
        return "";
    }

    @PostMapping("login")
    public Boolean login(@RequestBody UserDTO user){
        return deGovern.login(user.getId(),user.getPk());
    }


    @GetMapping("id")
    public User selectUserInfo(@RequestParam("userID") BigInteger userID){

        User user = new User();

        return user;
    }
}
