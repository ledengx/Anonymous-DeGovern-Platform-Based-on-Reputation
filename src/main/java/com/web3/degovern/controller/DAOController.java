package com.web3.degovern.controller;

import com.web3.degovern.entity.DAO;
import com.web3.degovern.service.DeGovernService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dao")
public class DAOController {

    @Autowired
    private DeGovernService deGovern;

    @GetMapping("add")
    public BigInteger createDAO(@RequestParam("name") String name,
                                @RequestParam("content") String content,
                                @RequestParam("DAOContract") String DAOContract,
                                @RequestParam("reputationBaseline") Integer reputationBaseline) {
        BigInteger base = BigInteger.valueOf(reputationBaseline);
        return deGovern.createDAO(name, content, DAOContract, base);
    }

    //分页查询 -- DAO
    @GetMapping("page")
    public Map<String, Object> selectByPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) throws ContractException {
        Map<String, Object>  res = new HashMap<>();
        pageNum = (pageNum - 1) * pageSize;
        BigInteger total = deGovern.getDAOTotal();
        List<DAO> data = deGovern.selectByPageDAO(pageNum, pageSize,total);
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    @GetMapping("update")
    public Boolean updateContract(@RequestParam String address, @RequestParam String DAOid){


        return true;
    }

    @GetMapping("num")
    public BigInteger getDAONum() throws ContractException {
        return deGovern.getDAOTotal();
    }

    @GetMapping("proposal")
    public BigInteger getDAOProposal(@RequestParam("id") Integer id) throws ContractException {
        BigInteger DAOid = BigInteger.valueOf(id);
        return deGovern.getProposalTotoal(DAOid);
    }
}
