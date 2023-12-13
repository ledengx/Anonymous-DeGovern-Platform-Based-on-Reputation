pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

contract DeGovern{

    address admin;

    //用户信息
    struct UserInfo{
        uint256 id;
        address userAddress;
        string userName;
        uint256 reputation;
        uint256 trustEvent;
        uint256 distrustEvent;
        string pk;//先把公钥当成密码
        uint level;//用户等级
    }

    //社区信息
    struct DAOInfo{
        uint256 id;
        string name;
        string content;
        address DAOContract;
        uint256 reputationBaseline;
    }

    //提案信息
    struct ProposalInfo{
        uint256 id;
        address proposalAddress;
        bool isContract;//是合约变更
        string proposalName;
        string proposalContent;
        bool isPass;
        uint256 yes;
        uint256 no;
        uint256 voter;
        uint256 start;
        uint256 stop;
    }

    //选票信息
    struct BallotInfo{
        uint256 id;
        uint256 proposalID;
        address userAddress;
        uint256 vote;
        bool choice;
        address proposalAddress;
    }
    //存用户和DAO
    UserInfo[] public users;
    DAOInfo[] public DAOs;

    mapping(uint256 => ProposalInfo[]) public proposals;//DAO -> proposals
    mapping(uint256 => BallotInfo[]) public ballots;//proposal -> ballots

    constructor (){
        admin = msg.sender;
    }

    modifier _onlyAdmin(){
        require(msg.sender == admin);
        _;
    }

    //创建选票
    function _createBallot(UserInfo _user, bool _choice, uint256 proposalID) private returns (BallotInfo){
        BallotInfo memory ballot;
        ballot.proposalID = proposalID;
        ballot.choice = _choice;
        ballot.vote = _user.level;
        ballot.userAddress = _user.userAddress;
        return ballot;
    }

    //创建用户
    function createUser(UserInfo user) public returns (uint256){
        user.id = users.length;
        users.push(user);
        return user.id;
    }

    //登录
    function login(uint256 id, string pk) public returns (bool){
        if (keccak256(pk) == keccak256(users[id].pk)){
            return true;
        }
        return false;
    }

    //创建DAO
    function createDAO(string name, string content, address contractAddress, uint256 Baseline) public returns (uint256){
        DAOInfo memory dao;
        dao.id = DAOs.length;
        dao.name = name;
        dao.content = content;
        dao.DAOContract = contractAddress;
        dao.reputationBaseline = Baseline;
        DAOs.push(dao);
        return dao.id;
    }

    //创建提案
    function createProposal(ProposalInfo proposal, uint256 DAOid, uint256 userID) public returns (bool){
        bool res = false;
        if (users[userID].reputation >= DAOs[DAOid].reputationBaseline){
            proposal.id = proposals[DAOid].length;
            proposal.isPass = false;
            //提案时间
            proposal.start = block.timestamp;
            proposal.stop = uint256(proposal.id + 1) * 86400 + block.timestamp;
            proposals[DAOid].push(proposal);
            res = true;
        }
        return res;
    }

    //投票
    function vote(bool _choice, uint256 userID, uint256 DAOid, uint256 proposalID) public returns (bool){
        bool res = false;
        if (users[userID].reputation >= DAOs[DAOid].reputationBaseline){
            //创建投票
            BallotInfo memory ballot = _createBallot(users[userID], _choice, proposalID);
            ballot.id = ballots[proposalID].length;
            if (ballot.choice) {
                proposals[DAOid][proposalID].yes = ballot.vote;

            }else{
                proposals[DAOid][proposalID].no = ballot.vote;
            }
            ballots[proposalID].push(ballot);
            res = true;
        }
        return res;
    }

    //是否截止
    function _checkTimes(uint256 proposalID, uint256 DAOid) private returns(bool){
        bool res = false;
        uint256 stop = proposals[DAOid][proposalID].stop;
        if (block.timestamp >= stop){
            res = true;
        }
        return res;
    }

    //是否通过
    function checkPass(uint256 proposalID, uint256 DAOid) public returns (bool){
        if (_checkTimes(proposalID,DAOid)){
            uint256 _yes = proposals[DAOid][proposalID].yes;
            uint256 _no = proposals[DAOid][proposalID].no;
            if (_yes > _no){
                proposals[DAOid][proposalID].isPass = true;
            }
            return proposals[DAOid][proposalID].isPass;
        }
        return false;
    }

    //更新声誉
    function updateReputation(uint256 id, uint256 reputation) public returns (bool) {
        //verify
        users[id].reputation = reputation;
        return true;
    }

    //查该社区的提案
    function selectProposal(uint256 DAOid) public view returns (ProposalInfo[]){
        return proposals[DAOid];
    }

    //查看该提案的所有票
    function selectBallot(uint256 proposalID) public view returns (BallotInfo[]){
        return ballots[proposalID];
    }

    //查询声誉迭代数据
    function getRepData(uint256 id)public view returns (uint256, uint256, uint256){
        return (users[id].trustEvent, users[id].distrustEvent, users[id].reputation);
    }

    function setTrust(uint256 num, uint256 userID) public {
        users[userID].trustEvent = users[userID].trustEvent + num;
    }

    function setDisTrust(uint256 num, uint256 userID) public {
        users[userID].distrustEvent = users[userID].distrustEvent + num;
    }
}
