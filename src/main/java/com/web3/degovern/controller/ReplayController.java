package com.web3.degovern.controller;
import com.web3.degovern.controller.dto.Chongfang;
import com.web3.degovern.service.ReplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@CrossOrigin
@RequestMapping("replay")
public class ReplayController {
    @Autowired
    private ReplayService replayService;

    private static final Logger log = LoggerFactory.getLogger(ReplayController.class);


    @GetMapping("balanceOf")
    public BigInteger balanceOf(@RequestParam("n") String n)throws Exception{
        return replayService.balanceof(n);
    }

    @GetMapping("getnonce")
    public BigInteger getnonce(@RequestParam("n") String n)throws Exception{
        return replayService.getnonce(n);
    }


    @GetMapping("transfer")
    public String transfer(@RequestParam("sender") String sender,
                           @RequestParam("receiver") String receiver,
                           @RequestParam("amount") BigInteger amount,
                           @RequestParam("signature") BigInteger signature,
                           @RequestParam("nonce") BigInteger nonce)throws Exception{
        return replayService.transfer(sender, receiver, amount, signature, nonce);
    }

    @GetMapping("chongfangak")
    public Chongfang chongfangak(@RequestParam("A") String A,
                                 @RequestParam("B") String B,
                                 @RequestParam("C") String C,
                                 @RequestParam("amount1") BigInteger amount1,
                                 @RequestParam("amount2") BigInteger amount2,
                                 @RequestParam("amount3") BigInteger amount3)throws Exception{
        return replayService.replayak(A, B, C,amount1, amount2,amount3);
    }

}


