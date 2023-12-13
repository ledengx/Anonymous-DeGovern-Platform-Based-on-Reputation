package com.web3.degovern.controller;

import com.web3.degovern.controller.dto.RegisterDTO;
import com.web3.degovern.controller.dto.UserDTO;
import com.web3.degovern.entity.Login;
import com.web3.degovern.entity.User;
import com.web3.degovern.service.DeGovernService;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private DeGovernService deGovern;

    @PostMapping("register")
    public List<String> register(@RequestBody RegisterDTO registerDTO){
        return deGovern.register(registerDTO);
    }

    @PostMapping("login")
    public Login login(@RequestBody UserDTO user) throws ContractException {
        Login res =  new Login();
        res.setAns(deGovern.login(user.getUserAddress(),user.getPassword()));
        res.setUserAddress(user.getUserAddress());
        return res;
    }


    @GetMapping("id")
    public User selectUserInfo(@RequestParam("userAddress") String userAddress) throws ContractException {

        return deGovern.selectUserInfo(userAddress);
    }


    @GetMapping("num")
    public BigInteger getUserNum() throws ContractException {
        return deGovern.getUsersNum();
    }

    @GetMapping("changeRep")
    public Boolean changeRep(@RequestParam("userAddress") String userAddress, @RequestParam("reputation") Integer reputation){
        BigInteger rep = BigInteger.valueOf(reputation);
        System.out.println(rep);
        return deGovern.setRep(userAddress, rep);
    }

}



