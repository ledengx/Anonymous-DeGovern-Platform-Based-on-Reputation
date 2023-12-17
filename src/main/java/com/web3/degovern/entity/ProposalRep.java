package com.web3.degovern.entity;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ProposalRep {
    private BigInteger ID;
    private String proposalAddress;
    private String userAddress;
    private String isContract;
    private String proposalName;
    private String proposalContent;
    private String status;
    private String yes;
    private String no;
    private BigInteger voter;
    private BigInteger start;
    private BigInteger stop;
}
