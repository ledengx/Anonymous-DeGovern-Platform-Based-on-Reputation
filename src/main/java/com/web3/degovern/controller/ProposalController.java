package com.web3.degovern.controller;

import com.web3.degovern.controller.dto.ProposalDTO;
import com.web3.degovern.entity.DAO;
import com.web3.degovern.entity.Proposal;
import com.web3.degovern.entity.ProposalRep;
import com.web3.degovern.service.DeGovernService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Formatter;
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
        if (deGovern.createProposal(proposal)){
            System.out.println("提案创建成功");
            //deGovern.setEvent(proposal.getUserAddress(), true, BigInteger.valueOf(1));
            return true;
        }
        System.out.println("提案创建失败");

        return false;
    }

    @GetMapping("page")
    public Map<String, Object> selectByPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer id) throws ContractException {
        deGovern.updateProposalStatus(id);
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
    public ProposalRep selectProposalInfo(@RequestParam("daoid") Integer daoid, @RequestParam("proposalid") Integer proposalid) throws Exception {
        BigInteger DAOid = BigInteger.valueOf(daoid);

        return deGovern.selectProposalInfo(DAOid, proposalid);
    }

    @GetMapping("every")
    public BigInteger getProposalNum(@RequestParam("id") Integer id) throws ContractException {
        BigInteger DAOid = BigInteger.valueOf(id);
        return deGovern.getProposalTotoal(DAOid);
    }

    @GetMapping("type")
    public Map<String, Integer> selectType() throws ContractException {
        Map<String, Integer> res = new HashMap<>();
        List<Proposal> proposals = deGovern.selectAllProposal();
        Integer upgrade = 0;
        Integer others = 0;
        for (Proposal p : proposals){
            switch (p.getIsContract()) {
                case "true" -> upgrade++;
                case "false" -> others++;
            }
        }
        res.put("upgrade", upgrade);
        res.put("others", others);
        return res;

    }

    @GetMapping("status")
    public Map<String, Integer> selectStatus() throws ContractException {
        Map<String, Integer> res = new HashMap<>();
        Integer voting = 0;
        Integer pass = 0;
        Integer noPass = 0;
        List<Proposal> proposals = deGovern.selectAllProposal();
        for (Proposal p : proposals){
            switch (p.getStatus()) {
                case "投票中" -> voting++;
                case "已通过" -> pass++;
                case "未通过" -> noPass++;
            }
        }
        res.put("voting", voting);
        res.put("pass", pass);
        res.put("noPass", noPass);
        return res;
    }

}
