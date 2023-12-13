package com.web3.degovern.service;

import com.web3.degovern.conf.ContractConfig;
import com.web3.degovern.contracts.DeGovern;
import com.web3.degovern.controller.dto.ProposalDTO;
import com.web3.degovern.controller.dto.RegisterDTO;
import com.web3.degovern.entity.DAO;
import com.web3.degovern.entity.Proposal;
import com.web3.degovern.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple7;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Data
@Service
@NoArgsConstructor
public class DeGovernService {

    private static BigInteger Num = BigInteger.valueOf(0);

    @Autowired
    private Client client;

    @Autowired
    private ContractConfig contractConfig;

    AssembleTransactionProcessor txProcessor;

    private DeGovern deGovern;

    @PostConstruct
    //项目部署时运行一次
    public void init() throws Exception {
        this.txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        //判断在智能合约配置类里，该智能合约是否已经有地址
        if(contractConfig.getDeGovernAddress() == null ||
                contractConfig.getDeGovernAddress().isEmpty()){
            //如果没有的话，将其部署到链上
            deGovern = DeGovern.deploy(client,client.getCryptoSuite().getCryptoKeyPair());
            System.out.println("address = " + deGovern.getContractAddress());
            //将该合约的地址填写到智能合约配置类中
            contractConfig.setDeGovernAddress(deGovern.getContractAddress());
        }else{
            //如果已有地址，则加载
            deGovern = DeGovern.load(
                    contractConfig.getDeGovernAddress(),
                    client,
                    client.getCryptoSuite().getCryptoKeyPair()
            );
        }
        ;
    }

    //公私钥生成方法
    private static CryptoKeyPair getKeyPair(){
        // 创建非国密类型的CryptoSuite
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        // 随机生成非国密公私钥对
        return cryptoSuite.createKeyPair();
    }

    //声誉计算公式
    private static BigInteger RepCompute(){

        return BigInteger.valueOf(1);
    }


    /*                    用户                        */
    //创建用户
    public List<String> register(RegisterDTO registerDTO){
        List<String> res = new ArrayList<>();
        CryptoKeyPair account = getKeyPair();
        res.add(account.keyPair.getPublic().toString());
        res.add(account.keyPair.getPrivate().toString());
        res.add(account.getAddress());
        System.out.println(account.keyPair);
        deGovern.register(account.getAddress(), registerDTO.getUserName(), registerDTO.getPassword(), res.get(0));
        return res;
    }

    //登录
    public Boolean login(String userAddress, String password) throws ContractException {
        BigInteger userID = deGovern.getUserID(userAddress);
        TransactionReceipt receipt = deGovern.login(userID, password);
        return deGovern.getLoginOutput(receipt).getValue1();
    }

    public User selectUserInfo(String userAddress) throws ContractException {
        BigInteger userID = deGovern.getUserID(userAddress);
        Tuple7<BigInteger, String, String, byte[], BigInteger, String, BigInteger> userInfo = deGovern.users(userID);
        User user = new User();
        user.setID(userInfo.getValue1());
        user.setUserName(userInfo.getValue2());
        user.setUserAddress(userInfo.getValue3());
        user.setReputation(userInfo.getValue5());
        user.setLevel(userInfo.getValue7());
        return user;
    }
    //获取用户总数
    public BigInteger getUsersNum() throws ContractException {
        return deGovern.getUsersNum();
    }

    public Boolean setRep(String userAddress, BigInteger num){
        deGovern.setRep(userAddress, num);
        return true;
    }


    /*                   DAO社区                     */
    //创建社区
    public BigInteger createDAO(String DAOName, String content, String address, BigInteger baseline){
        TransactionReceipt dao = deGovern.createDAO(DAOName, content, address, baseline);
        return deGovern.getCreateDAOOutput(dao).getValue1();
    }

    //获取社区数量
    public BigInteger getDAOTotal() throws ContractException {
        return deGovern.getDAOsNum();
    }

    //针对社区的分页查询
    public List<DAO> selectByPageDAO(Integer pageNum, Integer pageSize, BigInteger total) throws ContractException {
        List<DAO> data = new ArrayList<>();
        int flag = pageNum + pageSize;
        if (flag >= total.intValue()){
            flag = total.intValue();
        }
        for (int i = pageNum; i < flag; i++){
            DeGovern.Struct1 daoInfo = deGovern.selectDAOInfo(BigInteger.valueOf(i));
            DAO dao = new DAO();
            dao.setID(daoInfo.id);
            dao.setName(daoInfo.name);
            dao.setContent(daoInfo.content);
            dao.setDAOContract(daoInfo.DAOContract);
            dao.setReputationBaseline(daoInfo.reputationBaseline);
            data.add(dao);
        }
        return data;
    }




    /*                提案                    */

    //创建提案
    public Boolean createProposal(ProposalDTO proposalDTO) throws ContractException {
        DeGovern.Struct3 proposal = new DeGovern.Struct3(Num, proposalDTO.getProposalAddress(), proposalDTO.getUserAddress(),
                                                        proposalDTO.getIsContract(), proposalDTO.getProposalName(), proposalDTO.getProposalContent(),
                                                        "投票中", Num, Num, Num, Num, Num);
        BigInteger userID = deGovern.getUserID(proposalDTO.getUserAddress());
        TransactionReceipt receipt = deGovern.createProposal(proposal, BigInteger.valueOf(proposalDTO.getId()), userID);
        return deGovern.getCreateProposalOutput(receipt).getValue1();
    }

    //获取该社区下的提案总数
    public BigInteger getProposalTotoal(BigInteger DAOid) throws ContractException {
        return deGovern.proposalsLength();
    }

    //获取提案详情信息


    public Proposal selectProposalInfo(BigInteger DAOid, Integer proposalID) throws ContractException {
        Proposal proposal = new Proposal();
        DynamicArray<DeGovern.Struct3> array = deGovern.selectProposal(DAOid);
        DeGovern.Struct3 struct3 = array.getValue().get(proposalID);
        proposal.setID(struct3.id);
        proposal.setProposalAddress(struct3.proposalAddress);
        proposal.setUserAddress(struct3.userAddress);
        if (struct3.isContract){
            proposal.setIsContract("升级合约");
        }else {
            proposal.setIsContract("其他合约");
        }
        proposal.setProposalName(struct3.proposalName);
        proposal.setProposalContent(struct3.proposalContent);
        proposal.setStatus(struct3.status);
        proposal.setYes(struct3.yes);
        proposal.setNo(struct3.no);
        proposal.setVoter(struct3.voter);
        proposal.setStart(struct3.start);
        proposal.setStop(struct3.stop);
        return proposal;
    }

    //针对该社区下所有提案的分页查询
    public List<Proposal> selectByPageProposal(Integer pageNum, Integer pageSize, BigInteger total, BigInteger DAOid) throws ContractException {
        List<Proposal> data = new ArrayList<>();
        int flag = pageNum + pageSize;
        if (flag >= total.intValue()){
            flag = total.intValue();
        }
        DynamicArray<DeGovern.Struct3> proposals = deGovern.selectProposal(DAOid);
        List<DeGovern.Struct3> value = proposals.getValue();
        for (int i = pageNum; i < flag; i++){
            DeGovern.Struct3 proposalInfo = value.get(i);
            Proposal proposal = new Proposal();
            proposal.setID(proposalInfo.id);
            proposal.setProposalAddress(proposalInfo.proposalAddress);
            proposal.setUserAddress(proposalInfo.userAddress);
            if (proposalInfo.isContract){
                proposal.setIsContract("升级合约");
            }else{
                proposal.setIsContract("其他合约");
            }
            proposal.setProposalName(proposalInfo.proposalName);
            proposal.setProposalContent(proposalInfo.proposalContent);
            proposal.setStatus(proposalInfo.status);
            proposal.setYes(proposalInfo.yes);
            proposal.setNo(proposalInfo.no);
            proposal.setVoter(proposalInfo.voter);
            proposal.setStart(proposalInfo.start);
            proposal.setStop(proposalInfo.stop);
            data.add(proposal);
        }
        return data;

    }


    /*                  治理                 */
    //投票
    public Boolean vote(Boolean choice, String userAddress, BigInteger DAOid, BigInteger proposalID) throws ContractException {
        BigInteger userID = deGovern.getUserID(userAddress);
        TransactionReceipt receipt = deGovern.vote(choice, userID, DAOid, proposalID);
        return deGovern.getVoteOutput(receipt).getValue1();
    }


}