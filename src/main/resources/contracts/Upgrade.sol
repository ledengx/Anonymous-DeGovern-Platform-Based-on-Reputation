// SPDX-License-Identifier: MIT
pragma solidity ^0.4.25;

contract dappStorageStructure {
    address public dappAddress;
    address public dappUserAddress;
    address public admin; // admin of the contract
    address public deGovern;
    struct Info {
        string userName;
        uint userId;
        uint userReputation;
    }
    mapping(address => Info) public userInfo;
}

contract dapp is dappStorageStructure {
    //当前版本version@1.0
    constructor() {
        admin = msg.sender;
    }
    modifier onlyAdmin() {
        require(msg.sender == admin, "Only admin");
        _;
    }
    modifier onlyAdminOrUser(address _userAddress) {
        require(msg.sender == admin || msg.sender == _userAddress, "Only admin or user itself");
        _;
    }
    function setUserReputation(uint _userReputation) public onlyAdmin {
        userInfo[dappUserAddress].userReputation = _userReputation;
    }
    function setUserInfo(address _userAddress, string memory _userName, uint _userId, uint _userReputation) public onlyAdmin {
        dappUserAddress = _userAddress;
        userInfo[dappUserAddress].userName = _userName;
        userInfo[dappUserAddress].userId = _userId;
        setUserReputation(_userReputation);
    }
    function changeUserName(address _userAddress, string memory _newName) public onlyAdminOrUser(_userAddress) {
        userInfo[_userAddress].userName = _newName;
    }
    function updateUserReputation(address _userAddress) public onlyAdmin {
        userInfo[_userAddress].userReputation += 1;
    }
}

contract Upgrade is dappStorageStructure {
    constructor(address _deGovern) {
        admin = msg.sender;
        deGovern = _deGovern;
    }

    modifier onlyAdmin() {
        require(msg.sender == admin, "Only admin can operate");
        _;
    }

    modifier onlyDeGovern() {
        require(msg.sender == deGovern, "only deGovern can operate");
        _;
    }

    modifier onlyAdminOrUser(address _userAddress) {
        require(msg.sender == admin || msg.sender == _userAddress, "Only admin or user itself");
        _;
    }

    modifier onlyAdminOrDeGovern() {
        require(msg.sender == admin || msg.sender == deGovern, "Only admin or DeGovern");
        _;
    }


    function updateDappAddress(address _newDappAddress) public onlyAdminOrDeGovern {
        require(dappAddress != _newDappAddress, "error address");

        setDappAddress(_newDappAddress);
    }

    function setDappAddress(address _dappAddress) public onlyAdmin {
        dappAddress = _dappAddress;
    }


    function _setUserReputation(uint _userReputation) internal {
        (bool success,) = dappAddress.delegatecall(abi.encodeWithSignature("setUserReputation(uint256)", _userReputation));
        require(success, "Error in setting user reputation");
        // dappAddress.delegatecall(abi.encodeWithSignature("setUserReputation(uint256)", _userReputation));
    }

    function setUserInfo(address _userAddress, string memory _userName, uint _userId, uint _userReputation) public onlyAdmin{
        dappUserAddress = _userAddress;
        userInfo[_userAddress].userName = _userName;
        userInfo[_userAddress].userId = _userId;
        _setUserReputation(_userReputation);
    }

    function updateUserReputation(address _userAddress) public onlyAdmin {
        (bool success,) = dappAddress.delegatecall(abi.encodeWithSignature("updateUserReputation(address)", _userAddress));
        require(success, "Error in updating user reputation");
    }

    function chageUserName(address _userAddress, string memory _newName) public onlyAdminOrUser(_userAddress) {
        (bool success,) = dappAddress.delegatecall(abi.encodeWithSignature("changeUserName(string)", _newName));
        require(success, "Error in changing user name");
    }

    function getUserInfo(address _userAddress) public view onlyAdminOrUser(_userAddress) returns (string memory, uint, uint) {
        return (userInfo[_userAddress].userName, userInfo[_userAddress].userId, userInfo[_userAddress].userReputation);
    }

    function getUserName(address _userAddress) public view onlyAdminOrUser(_userAddress) returns (string memory) {
        return userInfo[_userAddress].userName;
    }

    function getUserId(address _userAddress) public view onlyAdminOrUser(_userAddress) returns (uint) {
        return userInfo[_userAddress].userId;
    }

    function getUserReputation(address _userAddress) public view onlyAdminOrUser(_userAddress) returns (uint) {
        return userInfo[_userAddress].userReputation;
    }

    // pre-write function
    function updateUserId(address _userAddress, uint _newUserId) public onlyAdminOrUser(_userAddress) {
        (bool success,) = dappAddress.delegatecall(abi.encodeWithSignature("updateUserId(address, uint)", _userAddress, _newUserId));
        require(success, "Error in updating user id");
    }


}


contract upgradedDapp is dappStorageStructure {
    //当前版本version@2.0
    //新版本合约
    constructor() {
        admin = msg.sender;
    }
    modifier onlyAdmin() {
        require(msg.sender == admin, "Only admin can set dapp address");
        _;
    }
    modifier onlyAdminOrUser(address _userAddress) {
        require(msg.sender == admin || msg.sender == _userAddress, "Only admin or user itself");
        _;
    }
    function setUserReputation(uint _userReputation) public onlyAdmin {
        userInfo[dappUserAddress].userReputation = _userReputation;
    }
    function setUserInfo(address _userAddress, string memory _userName, uint _userId, uint _userReputation) public onlyAdmin {
        dappUserAddress = _userAddress;
        userInfo[dappUserAddress].userName = _userName;
        userInfo[dappUserAddress].userId = _userId;
        setUserReputation(_userReputation);
    }
    function changeUserName(address _userAddress, string memory _newName) public onlyAdminOrUser(_userAddress) {
        userInfo[_userAddress].userName = _newName;
    }
    function updateUserReputation(address _userAddress) public onlyAdmin {
        userInfo[_userAddress].userReputation += 10;
    }
}
