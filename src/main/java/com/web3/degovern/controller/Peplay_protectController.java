package com.web3.degovern.controller;
import com.web3.degovern.contracts.Replay_protect;
import com.web3.degovern.controller.dto.Chongfang;
import com.web3.degovern.controller.dto.Chongfangpt;
import com.web3.degovern.service.Replay_protectService;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@CrossOrigin
@RequestMapping("replay_protect")
public class Peplay_protectController {
    @Autowired
    private Replay_protectService replay_protectService;


    @GetMapping("pbalanceOf")
    public BigInteger pbalanceOf(@RequestParam("n") String n)throws Exception{
        return replay_protectService.pbalanceof(n);
    }

    @GetMapping("pgetnonce")
    public BigInteger pgetnonce(@RequestParam("n") String n)throws Exception{
        return replay_protectService.pgetnonce(n);
    }


    @GetMapping("ptransfer")
    public String ptransfer(@RequestParam("sender") String sender,
                           @RequestParam("receiver") String receiver,
                           @RequestParam("amount") BigInteger amount,
                           @RequestParam("signature") BigInteger signature,
                           @RequestParam("nonce") BigInteger nonce,
                            @RequestParam("timestamp") BigInteger timestamp)throws Exception{
        return replay_protectService.ptransfer(sender, receiver, amount, signature, nonce,timestamp);
    }

    @GetMapping("pgetLastTimestamp")
    public BigInteger pgetLastTimestamp(@RequestParam("n") String n) throws Exception{
        return replay_protectService.pgetLastTimestamp(n);
    }

    @GetMapping("chongfangpk")
    public Chongfangpt replay_protectService(@RequestParam("A") String A,
                                             @RequestParam("B") String B,
                                             @RequestParam("C") String C)throws Exception{
        return replay_protectService.replaypt(A, B, C);
    }



}
