package com.web3.degovern.controller;

import com.web3.degovern.controller.dto.ProposalDTO;
import com.web3.degovern.entity.DAO;
import com.web3.degovern.entity.Proposal;
import com.web3.degovern.service.DeGovernService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("proposal")
public class ProposalController {

    @Autowired
    private DeGovernService deGovern;

    @PostMapping("add")
    public Boolean createProposal(@RequestBody ProposalDTO proposal) throws ContractException {
        return deGovern.createProposal(proposal);
    }

    @GetMapping("page")
    public Map<String, Object> selectByPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer id) throws ContractException {
        Map<String, Object> res = new HashMap<>();
        pageNum = (pageNum - 1) * pageSize;
        BigInteger DAOid = BigInteger.valueOf(id);
        BigInteger total = deGovern.getProposalTotoal(DAOid);
        List<Proposal> data = deGovern.selectByPageProposal(pageNum, pageSize, total, DAOid);
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    @GetMapping("num")
    public int getProposalNum() throws ContractException {
        int res = 0;
        int flag = deGovern.getDAOTotal().intValue();
        for (int i = 0; i < flag; i++){
            res = res + deGovern.getProposalTotoal(BigInteger.valueOf(i)).intValue();
        }
        return res;
    }

    @GetMapping("detail")
    public Proposal selectProposalInfo(@RequestParam("daoid") Integer daoid, @RequestParam("proposalid") Integer proposalid) throws ContractException {
        BigInteger DAOid = BigInteger.valueOf(daoid);
        return deGovern.selectProposalInfo(DAOid, proposalid);
    }

}
