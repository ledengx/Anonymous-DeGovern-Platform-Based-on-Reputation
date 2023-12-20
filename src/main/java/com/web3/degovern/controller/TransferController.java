package com.web3.degovern.controller;
import com.web3.degovern.controller.dto.*;
import com.web3.degovern.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("transfer")
public class TransferController {
    @Autowired
    private TransferService transferService;



    @GetMapping("zshuanghuagongji")
    public Shuanghuaak zshuanghuagongji(@RequestParam("A") String A,
                                                     @RequestParam("B") String B,
                                                     @RequestParam("C") String C,
                                                     @RequestParam("balance") BigInteger balance,
                                                     @RequestParam("amount") BigInteger amount)throws Exception{
        Shuanghuaak shuanghuaak=transferService.zshuanghuagongji(A,B,C,balance,amount);
        System.out.println(shuanghuaak);
        return shuanghuaak;
    }

    @GetMapping("shuanghuapt")
    public Shuanghuapt shuanghuapt (@RequestParam("A") String A,
                                 @RequestParam("B") String B,
                                 @RequestParam("C") String C,
                                 @RequestParam("timelock") BigInteger timelock)throws Exception{
        return transferService.shuanghuapt(A,B,C,timelock);
    }


    @GetMapping("timestampak")
    public Timestampat timestampak(@RequestParam("A") String A,
                                                     @RequestParam("B") String B,
                                                     @RequestParam("amount") BigInteger amount)throws Exception{
        return transferService.timestampat(A,B,amount);
    }

    @GetMapping("timestamppt")
    public Timestamppt timestamppt(@RequestParam("A") String A,
                                   @RequestParam("B") String B,
                                   @RequestParam("C") String C)throws Exception{
        return transferService.timestamppt(A,B,C);
    }

    @GetMapping("shunxuak")
    public Shunxuak shunxuak(@RequestParam("A") String A,
                                   @RequestParam("B") String B,
                             @RequestParam("C") String C)throws Exception{
        return transferService.shunxuak(A,B,C);
    }

    @GetMapping("shunxupt")
    public Shunxupt shunxupt(@RequestParam("A") String A,
                             @RequestParam("B") String B,
                             @RequestParam("C") String C)throws Exception{
        return transferService.shunxupt(A,B,C);
    }

    @GetMapping("chongruak")
    public Chongruak chongruak(@RequestParam("A") String A,
                             @RequestParam("B") String B,
                             @RequestParam("C") String C,
                               @RequestParam("amount1") BigInteger amount1,
                               @RequestParam("amount2") BigInteger amount2,
                               @RequestParam("amount3") BigInteger amount3,
                             @RequestParam("value") BigInteger value)throws Exception{
        return transferService.chongruak(A,B,C,amount1,amount2,amount3,value);
    }

    @GetMapping("chongrupt")
    public Chongrupt chongrupt(@RequestParam("A") String A,
                               @RequestParam("B") String B,
                               @RequestParam("C") String C)throws Exception{
        return transferService.chongrupt(A,B,C);
    }

    @GetMapping("jujueak")
    public Jujueak jujueak(@RequestParam("A") String A,
                           @RequestParam("B") String B,
                           @RequestParam("C") String C,
                           @RequestParam("amount1") BigInteger amount1,
                           @RequestParam("amount2") BigInteger amount2,
                           @RequestParam("amount3") BigInteger amount3)throws Exception{
        Jujueak jujueak = transferService.jujueak(A, B, C, amount1, amount2, amount3);
        System.out.println(jujueak+"================================");
        return jujueak;
    }

    @GetMapping("jujuept")
    public Jujuept jujuept(@RequestParam("A") String A,
                           @RequestParam("B") String B,
                           @RequestParam("C") String C)throws Exception{
        return transferService.jujuept(A, B, C);
    }

    @GetMapping("zuseak")
    public Zuseak zuseak(@RequestParam("A") String A,
                           @RequestParam("B") String B,
                           @RequestParam("C") String C,
                         @RequestParam("ini") BigInteger ini,
                         @RequestParam("amountak") BigInteger amount,
                         @RequestParam("timesak") BigInteger times)throws Exception{
        return transferService.Zuseak(A, B, C,ini,amount,times);
    }

    @GetMapping("zusept")
    public Zusept zusept(@RequestParam("A") String A,
                         @RequestParam("B") String B,
                         @RequestParam("C") String C,
                         @RequestParam("amountpt") BigInteger amount,
                         @RequestParam("timespt") BigInteger times)throws Exception{
        return transferService.Zusept(A, B, C,amount,times);
    }



}
