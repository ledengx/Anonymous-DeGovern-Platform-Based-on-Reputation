package com.web3.degovern.controller;

import com.web3.degovern.service.DeGovernService;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("vote")
public class VoteController {

    @Autowired
    private DeGovernService deGovern;

    @PostMapping("vote")
    public Boolean vote(@RequestParam("choice") Boolean choice,
                        @RequestParam("userID")BigInteger userID,
                        @RequestParam("DAOid") BigInteger DAOid,
                        @RequestParam("proposalID") BigInteger proposalID){
        return deGovern.vote(choice, userID, DAOid, proposalID);
    }


}
