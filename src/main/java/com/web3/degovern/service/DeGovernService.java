package com.web3.degovern.service;

import com.web3.degovern.conf.ContractConfig;
import com.web3.degovern.contracts.DeGovern;
import com.web3.degovern.controller.dto.ProposalDTO;
import com.web3.degovern.controller.dto.RegisterDTO;
import com.web3.degovern.entity.*;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple12;
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
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

@Data
@Service
@NoArgsConstructor
public class DeGovernService {

    private static BigInteger Num = BigInteger.valueOf(0);
    private static double timeDec = 0.8;
    private static double punish = 0.7;


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
            System.out.println("DeGovern address = " + deGovern.getContractAddress());
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
    }

    private static final String MD5_ALGORITHM = "MD5";
    public static String encrypt(String data) throws Exception {
        // 获取MD5算法实例
        MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM);
        // 计算散列值
        byte[] digest = messageDigest.digest(data.getBytes());
        Formatter formatter = new Formatter();
        // 补齐前导0，并格式化
        for (byte b : digest) {
            formatter.format("%04x", b);
        }
        return formatter.toString();
    }
    //公私钥生成方法
    private static CryptoKeyPair getKeyPair(){
        // 创建非国密类型的CryptoSuite
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        // 随机生成非国密公私钥对
        return cryptoSuite.createKeyPair();
    }

    //声誉计算公式
    private static BigInteger RepCompute(int trustNum, int disTrustNum, int oldRep){
        /*double newRep = 0.0;
        if ((trustNum == 0)&&(disTrustNum == 0)){
            newRep = 0.0;
        }else {
            double trust = (double) trustNum / (trustNum+disTrustNum);
            double distTrust = (double) disTrustNum/ (trustNum+disTrustNum);
            newRep = trust-punish*distTrust;
        }

        System.out.println("newRep:   "+newRep+"oldRep:   "+oldRep);
        double RepTemp =  newRep * ( 1 - timeDec) + ((double)oldRep)*timeDec;
        int Rep = (int) RepTemp;*/
        System.out.println("oldRep:"+oldRep+"  "+"disTrust"+disTrustNum+ "trust:"+trustNum);
        Integer Rep = oldRep - (disTrustNum) + trustNum/2;
        System.out.println(Rep);
        return BigInteger.valueOf(Rep);
    }

    public void RepUpdate(String userAddress) throws ContractException {
        DynamicArray<DeGovern.Struct0> eventArray = deGovern.selectEvent(userAddress);
        BigInteger userID = deGovern.getUserID(userAddress);
        BigInteger oldRep = deGovern.users(userID).getValue5();
        int trust = 0;
        int disTrust = 0;
        List<DeGovern.Struct0> events = eventArray.getValue();
        for (int i = 0; i < events.size(); i++){
            System.out.println("events.isUsed:"+events.get(i).isUsed+"events.Type"+events.get(i).eventType+"events.level"+events.get(i).level);
            if (events.get(i).isUsed){

            }else{
                events.get(i).isUsed = true;
                if (events.get(i).eventType){
                    trust = trust + events.get(i).level.intValue();
                }else {
                    disTrust = disTrust + events.get(i).level.intValue();
                }
            }
        }
        BigInteger rep = RepCompute(trust, disTrust, oldRep.intValue());
        if (rep.intValue() < 0){
            rep = BigInteger.valueOf(0);
        }
        BigInteger level = rep;//暂定这样，后面还得写个隐射函数之类的
        System.out.println(rep+"<-rep"+"   "+level+"<-level");
        deGovern.setRep(userAddress, rep, level);

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
        Integer trust = 0;
        Integer distTrust = 0;
        Tuple7<BigInteger, String, String, byte[], BigInteger, String, BigInteger> userInfo = deGovern.users(userID);
        User user = new User();
        user.setID(userInfo.getValue1());
        user.setUserName(userInfo.getValue2());
        user.setUserAddress(userInfo.getValue3());
        user.setReputation(userInfo.getValue5());
        user.setLevel(userInfo.getValue7());
        DynamicArray<DeGovern.Struct0> struct0DynamicArray = deGovern.selectEvent(userAddress);
        List<DeGovern.Struct0> events = struct0DynamicArray.getValue();
        for (int i = 0; i < events.size(); i++){
            if (events.get(i).eventType){
                trust++;
            }else{
                distTrust++;
            }
        }
        user.setTrust(BigInteger.valueOf(trust));
        user.setDisTrust(BigInteger.valueOf(distTrust));
        return user;
    }
    //查询用户行为
    public List<DeGovern.Struct0> selectUserEvent(String userAddress) throws ContractException {
        DynamicArray<DeGovern.Struct0> array = deGovern.selectEvent(userAddress);

        return array.getValue();
    }
    //获取用户总数
    public BigInteger getUsersNum() throws ContractException {
        return deGovern.getUsersNum();
    }

    public Boolean setRep(String userAddress, BigInteger num){
        deGovern.setRep(userAddress, num, num);
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
            DeGovern.Struct2 daoInfo = deGovern.selectDAOInfo(BigInteger.valueOf(i));
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
        DeGovern.Struct1 proposal = new DeGovern.Struct1(Num, proposalDTO.getProposalAddress(), proposalDTO.getUserAddress(),
                                                        proposalDTO.getIsContract(), proposalDTO.getProposalName(), proposalDTO.getProposalContent(),
                                                        "投票中", Num, Num, Num, Num, BigInteger.valueOf(proposalDTO.getStop()*60));
        BigInteger userID = deGovern.getUserID(proposalDTO.getUserAddress());
        TransactionReceipt receipt = deGovern.createProposal(proposal, BigInteger.valueOf(proposalDTO.getId()), userID, BigInteger.valueOf(proposalDTO.getStop()));
        return deGovern.getCreateProposalOutput(receipt).getValue1();
    }

    //获取该社区下的提案总数
    public BigInteger getProposalTotoal(BigInteger DAOid) throws ContractException {
        DynamicArray<DeGovern.Struct1> proposals = deGovern.selectProposal(DAOid);
        BigInteger res = BigInteger.valueOf(proposals.getValue().size());
        return res;
    }

    //获取提案详情信息
    public ProposalRep selectProposalInfo(BigInteger DAOid, Integer proposalID) throws Exception {
        ProposalRep proposal = new ProposalRep();
        DynamicArray<DeGovern.Struct1> array = deGovern.selectProposal(DAOid);
        DeGovern.Struct1 struct1 = array.getValue().get(proposalID);
        proposal.setID(struct1.id);
        proposal.setProposalAddress(struct1.proposalAddress);
        proposal.setUserAddress(struct1.userAddress);
        if (struct1.isContract){
            proposal.setIsContract("升级合约");
        }else {
            proposal.setIsContract("其他合约");
        }
        proposal.setProposalName(struct1.proposalName);
        proposal.setProposalContent(struct1.proposalContent);
        proposal.setStatus(struct1.status);
        String cryptoYes = encrypt(struct1.yes.toString())+encrypt(struct1.proposalAddress)+encrypt(struct1.proposalContent);
        String yes = "密文:"+cryptoYes+"   明文:"+struct1.yes.toString();
        proposal.setYes(yes);
        String cryptoNo = encrypt(struct1.no.toString())+encrypt(struct1.id.toString())+encrypt(struct1.stop.toString());
        String no = "密文:"+cryptoNo+"   明文:"+struct1.no.toString();
        proposal.setNo(no);
        proposal.setVoter(struct1.voter);
        proposal.setStart(struct1.start);
        proposal.setStop(struct1.stop);
        return proposal;
    }

    //针对该社区下所有提案的分页查询
    public List<Proposal> selectByPageProposal(Integer pageNum, Integer pageSize, BigInteger total, BigInteger DAOid) throws ContractException {
        List<Proposal> data = new ArrayList<>();
        int flag = pageNum + pageSize;
        if (flag >= total.intValue()){
            flag = total.intValue();
        }
        DynamicArray<DeGovern.Struct1> proposals = deGovern.selectProposal(DAOid);
        List<DeGovern.Struct1> value = proposals.getValue();
        for (int i = pageNum; i < flag; i++){
            DeGovern.Struct1 proposalInfo = value.get(i);
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
    //获取提案状态
    public Boolean checkStatus(Integer proposalid, Integer daoid) throws ContractException {
        BigInteger proposalID = BigInteger.valueOf(proposalid);
        BigInteger DAOid = BigInteger.valueOf(daoid);
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
        Date date = new Date();
        deGovern.checkStatus(proposalID, DAOid, sdf.format(date));
        Tuple12<BigInteger, String, String, Boolean, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> proposal = deGovern.proposals(DAOid, proposalID);
        System.out.println(proposal.toString());
        if (proposal.getValue7().equals("投票中")){
            //提案没有过期
            return false;
        }else {
            return true;
        }

    }
    //更新该社区下的所有提案状态 id为该社区编号
    public void updateProposalStatus(Integer id) throws ContractException {
        deGovern.proposalsLength();
        DynamicArray<DeGovern.Struct1> array = deGovern.selectProposal(BigInteger.valueOf(id));
        List<DeGovern.Struct1> value = array.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
        Date date = new Date();
        for (int i = 0; i < value.size(); i++){
            deGovern.checkStatus(BigInteger.valueOf(i), BigInteger.valueOf(id), sdf.format(date));
        }
    }

    /*                  治理                 */
    //投票
    public Boolean vote(Boolean choice, String userAddress, BigInteger DAOid, BigInteger proposalID) throws ContractException {
        BigInteger userID = deGovern.getUserID(userAddress);
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
        Date date = new Date();
        TransactionReceipt receipt = deGovern.vote(choice, userID, DAOid, proposalID, sdf.format(date));
        return deGovern.getVoteOutput(receipt).getValue1();
    }

    public void setEvent(String userAddress, Boolean eventType, BigInteger level){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
        Date date = new Date();
        deGovern.setEvent(userAddress, level, sdf.format(date), eventType);
    }

    public Boolean checkDoubleVote(String userAddress, BigInteger proposalID) throws ContractException {
        DynamicArray<DeGovern.Struct3> array = deGovern.selectBallot(proposalID);
        System.out.println("==================异常没有报错");
        List<DeGovern.Struct3> ballots = array.getValue();
        for (DeGovern.Struct3 ballot : ballots) {
            String address = ballot.userAddress;
            if (address.equals(userAddress)) {
                return false;
            }
        }
        System.out.println("check查出来没有重复投票");
        return true;

    }

    public List<Upgrade> updateContract(){
        List<Upgrade> contractChange = new ArrayList<>();
        Upgrade oldContract = new Upgrade();
        oldContract.setName("verison@1.0");
        oldContract.setCode("contract dapp is dappStorageStructure {\n" +
                "    //当前版本version@1.0\n" +
                "    constructor() {\n" +
                "        admin = msg.sender;\n" +
                "    }\n" +
                "    modifier onlyAdmin() {\n" +
                "        require(msg.sender == admin, \"Only admin\");\n" +
                "        _;\n" +
                "    }\n" +
                "    modifier onlyAdminOrUser(address _userAddress) {\n" +
                "        require(msg.sender == admin || msg.sender == _userAddress, \"Only admin or user itself\");\n" +
                "        _;\n" +
                "    }\n" +
                "    function setUserReputation(uint _userReputation) public onlyAdmin {\n" +
                "        userInfo[dappUserAddress].userReputation = _userReputation;\n" +
                "    }\n" +
                "    function setUserInfo(address _userAddress, string memory _userName, uint _userId, uint _userReputation) public onlyAdmin {\n" +
                "        dappUserAddress = _userAddress;\n" +
                "        userInfo[dappUserAddress].userName = _userName;\n" +
                "        userInfo[dappUserAddress].userId = _userId;\n" +
                "        setUserReputation(_userReputation);\n" +
                "    }\n" +
                "    function changeUserName(address _userAddress, string memory _newName) public onlyAdminOrUser(_userAddress) {\n" +
                "        userInfo[_userAddress].userName = _newName;\n" +
                "    }\n" +
                "    function updateUserReputation(address _userAddress) public onlyAdmin {\n" +
                "        userInfo[_userAddress].userReputation += 1;\n" +
                "    }\n" +
                "}");
        oldContract.setInfo("这是该社区版本1.0");
        contractChange.add(oldContract);
        Upgrade newContract = new Upgrade();
        newContract.setName("version@2.0");
        newContract.setCode("contract upgradedDapp is dappStorageStructure {\n" +
                "    //当前版本version@2.0\n" +
                "    //新版本合约\n" +
                "    constructor() {\n" +
                "        admin = msg.sender;\n" +
                "    }\n" +
                "    modifier onlyAdmin() {\n" +
                "        require(msg.sender == admin, \"Only admin can set dapp address\");\n" +
                "        _;\n" +
                "    }\n" +
                "    modifier onlyAdminOrUser(address _userAddress) {\n" +
                "        require(msg.sender == admin || msg.sender == _userAddress, \"Only admin or user itself\");\n" +
                "        _;\n" +
                "    }\n" +
                "    function setUserReputation(uint _userReputation) public onlyAdmin {\n" +
                "        userInfo[dappUserAddress].userReputation = _userReputation;\n" +
                "    }\n" +
                "    function setUserInfo(address _userAddress, string memory _userName, uint _userId, uint _userReputation) public onlyAdmin {\n" +
                "        dappUserAddress = _userAddress;\n" +
                "        userInfo[dappUserAddress].userName = _userName;\n" +
                "        userInfo[dappUserAddress].userId = _userId;\n" +
                "        setUserReputation(_userReputation);\n" +
                "    }\n" +
                "    function changeUserName(address _userAddress, string memory _newName) public onlyAdminOrUser(_userAddress) {\n" +
                "        userInfo[_userAddress].userName = _newName;\n" +
                "    }\n" +
                "    function updateUserReputation(address _userAddress) public onlyAdmin {\n" +
                "        userInfo[_userAddress].userReputation += 10;\n" +
                "    }\n" +
                "}");
        newContract.setInfo("这是该社区版本2.0");
        contractChange.add(newContract);
        return contractChange;
    }


    public List<Proposal> selectAllProposal() throws ContractException {
        int daosNum = deGovern.getDAOsNum().intValue();
        List<Proposal> res = new ArrayList<>();
        for (int i = 0; i < daosNum; i++){
            DynamicArray<DeGovern.Struct1> array = deGovern.selectProposal(BigInteger.valueOf(i));
            List<DeGovern.Struct1> value = array.getValue();
            for (DeGovern.Struct1 struct1 : value) {
                Proposal temp = new Proposal();
                temp.setIsContract(struct1.isContract.toString());
                temp.setStatus(struct1.status);
                res.add(temp);
            }
        }
        return res;

    }






}