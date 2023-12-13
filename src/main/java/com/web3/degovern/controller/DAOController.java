package com.web3.degovern.controller;

import com.web3.degovern.entity.DAO;
import com.web3.degovern.entity.Proposal;
import com.web3.degovern.service.DeGovernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("dao")
public class DAOController {

    @Autowired
    private DeGovernService deGovern;

    @PostMapping("add")
    public BigInteger createDAO(@RequestParam("name") String name,
                                @RequestParam("content") String content,
                                @RequestParam("contractAddress") String contractAddress,
                                @RequestParam("Baseline")BigInteger Baseline) {
        return deGovern.createDAO(name, content, contractAddress, Baseline);
    }

    @PostMapping("proposal")
    public Boolean createProposal(@RequestBody Proposal proposal){
        return deGovern.createProposal(proposal);
    }


    /*@GetMapping("all")
    public List<DAO> selectDAO(){

       需要写成分页查询

    }*/


}
