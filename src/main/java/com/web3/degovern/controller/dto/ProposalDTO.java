package com.web3.degovern.controller.dto;


import lombok.Data;

import java.math.BigInteger;

@Data
public class ProposalDTO {

    private String proposalAddress;
    private Boolean isContract;
    private String proposalName;
    private String proposalContent;
    private Integer id;
    private String userAddress;

}
