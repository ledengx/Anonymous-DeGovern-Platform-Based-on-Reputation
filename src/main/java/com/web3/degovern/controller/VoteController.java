package com.web3.degovern.controller;

import com.web3.degovern.entity.Upgrade;
import com.web3.degovern.service.DeGovernService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

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
        System.out.println(daoid+"  "+proposalid);
        Boolean isDouble;
        try {
            isDouble = deGovern.checkDoubleVote(userAddress, BigInteger.valueOf(proposalid));
        }catch (Exception e){
            isDouble = false;
        }
        if (isDouble){
            System.out.println("没有重复投票");
            if(deGovern.checkStatus(proposalid, daoid)){//提案过期
                System.out.println("提案过期");
                return false;
            }else {
                System.out.println("提案未过期");
                BigInteger DAOid = BigInteger.valueOf(daoid);
                BigInteger ProposalID = BigInteger.valueOf(proposalid);
                return deGovern.vote(choice, userAddress, DAOid, ProposalID);
            }
        }else{
            System.out.println("重复投票");
            deGovern.setEvent(userAddress, false, BigInteger.valueOf(1));
            return false;
        }

    }

    @GetMapping("upgrade")
    public Upgrade upgradeContract(){
        return deGovern.updateContract().get(1);
    }

}
