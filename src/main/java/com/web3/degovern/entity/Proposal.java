package com.web3.degovern.entity;


import lombok.Data;

import java.math.BigInteger;

@Data
public class Proposal {

    private BigInteger ID;
    private String proposalAddress;
    private Boolean isContract;
    private String proposalName;
    private String proposalContent;
    private Boolean isPass;
    private BigInteger yes;
    private BigInteger no;
    private BigInteger voters;
    private BigInteger start;
    private BigInteger stop;
    private BigInteger DAOID;//所属DAO的ID
    private BigInteger userID;//提案发起者ID
}
