pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

contract DeGovern{
    address admin;
    //用户信息
    struct UserInfo{
        uint256 id;//这玩意儿主要用来链接用户的信息的
        string userName;
        address userAddress;
        bytes32 password;
        uint256 reputation;
        string pk;
        uint256 level;
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
        address userAddress;
        bool isContract;//是合约变更
        string proposalName;
        string proposalContent;
        string status;//提案状态
        uint256 yes;
        uint256 no;
        uint256 voter;
        uint256 start;
        uint256 stop;
    }
    struct EventInfo{
        bool eventType;//true为积极事件，false为恶意事件
        uint256 level;
        string time;
        bool isUsed;
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
    //存用户、DAO、事件
    UserInfo[] public users;
    DAOInfo[] public DAOs;

    //链接用户的ID
    mapping(address => uint256) usersID;

    //用户地址对应的事件组
    mapping(address => EventInfo[]) public events;

    mapping(uint256 => ProposalInfo[]) public proposals;//DAO -> proposals
    uint256 public proposalsLength = 0;

    mapping(uint256 => BallotInfo[]) public ballots;//proposal -> ballots
    uint256 public ballotsLength = 0;

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
    function register(address userAddress, string userName, string password, string pk) public{
        UserInfo memory user;
        user.id = users.length;
        user.userAddress = userAddress;
        user.userName = userName;
        user.password = keccak256(password);
        user.pk = pk;
        user.reputation = 0;
        user.level = 0;
        usersID[userAddress] = user.id;
        users.push(user);
    }

    //登录
    function login(uint256 id, string password) public returns (bool){
        if (keccak256(password) == users[id].password){
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
            proposal.status = "投票中";
            //提案时间
            proposal.start = block.timestamp;
            proposal.stop = uint256(proposal.id + 1) * 86400 + block.timestamp;
            proposals[DAOid].push(proposal);
            proposalsLength = proposalsLength + 1;
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
            ballotsLength = ballotsLength + 1;
            ballot.id = ballots[proposalID].length;
            if (ballot.choice) {
                proposals[DAOid][proposalID].yes = ballot.vote;

            }else{
                proposals[DAOid][proposalID].no = ballot.vote;
            }
            proposals[DAOid][proposalID].voter = proposals[DAOid][proposalID].voter + 1;
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
    function checkStatus(uint256 proposalID, uint256 DAOid) public returns (string){
        if (_checkTimes(proposalID,DAOid)){
            uint256 _yes = proposals[DAOid][proposalID].yes;
            uint256 _no = proposals[DAOid][proposalID].no;
            if (_yes > _no){
                proposals[DAOid][proposalID].status = "已通过";
            }else{
                return proposals[DAOid][proposalID].status;
            }
        }
        proposals[DAOid][proposalID].status = "未通过";
        return proposals[DAOid][proposalID].status;
    }

    //更新声誉
    function updateReputation(uint256 id, uint256 reputation) public returns (bool) {
        //verify
        users[id].reputation = reputation;
        return true;
    }

    //设置行为
    function setEvent(address userAddress, uint256 level, string time) public{
        EventInfo memory userEvent;
        userEvent.level = level;
        userEvent.time = time;
        events[userAddress].push(userEvent);
    }
    function getUserID(address userAddress) public view returns (uint256){
        return usersID[userAddress];
    }
    //获取用户总数
    function getUsersNum() public view returns(uint256) {
        return users.length;
    }
    //获取社区总数
    function getDAOsNum() public view returns(uint256) {
        return DAOs.length;
    }
    //获取DAO数量
    function selectDAOInfo(uint256 DAOid) public view returns(DAOInfo){
        return DAOs[DAOid];
    }
    //查该社区的提案
    function selectProposal(uint256 DAOid) public view returns (ProposalInfo[]){
        return proposals[DAOid];
    }
    //查看该提案的所有票
    function selectBallot(uint256 proposalID) public view returns (BallotInfo[]){
        return ballots[proposalID];
    }
    //查询用户行为
    function selectEvent(address userAddress) public view returns(EventInfo[]) {
        return events[userAddress];
    }
    //设置已用
    function setIsUsed(address userAddress, uint256 id) public {
        events[userAddress][id].isUsed = true;
    }
    //
    function setRep(address userAddress, uint256 num) public {
        uint256 id = usersID[userAddress];
        users[id].reputation = num;
    }

}
