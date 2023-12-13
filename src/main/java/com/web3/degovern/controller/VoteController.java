package com.web3.degovern.controller;

import com.web3.degovern.service.DeGovernService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("vote")
public class VoteController {

    @Autowired
    private DeGovernService deGovern;

    @GetMapping("work")
    public Boolean vote(@RequestParam("choice") Boolean choice,
                        @RequestParam("userAddress") String userAddress,
                        @RequestParam("daoid") Integer daoid,
                        @RequestParam("proposalid") Integer proposalid) throws ContractException {
        System.out.println(choice);
        System.out.println(userAddress);
        System.out.println(daoid);
        System.out.println(proposalid);
        BigInteger DAOid = BigInteger.valueOf(daoid);
        BigInteger ProposalID = BigInteger.valueOf(proposalid);
        return deGovern.vote(choice, userAddress, DAOid, ProposalID);
    }

}
