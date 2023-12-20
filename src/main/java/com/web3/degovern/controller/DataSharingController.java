package com.web3.degovern.controller;
import com.web3.degovern.controller.dto.*;
import com.web3.degovern.service.DataSharingService;
import com.web3.degovern.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("datasharing")
public class DataSharingController {
    @Autowired
    private DataSharingService dataSharingService;



    @GetMapping("jiechiak")
    public Jiechiak jiechiak(@RequestParam("A") String A,
                                                     @RequestParam("B") String B,
                                                     @RequestParam("C") String C,
                                                     @RequestParam("dataa") String Dataa,
                                                     @RequestParam("port") String port)throws Exception{
        System.out.println(A+B+C+Dataa+port);
        return dataSharingService.jiechiak(A,B,C,Dataa,port);
    }

    @GetMapping("jiechipt")
    public Jiechipt jiechipt(@RequestParam("A") String A,
                             @RequestParam("B") String B,
                             @RequestParam("C") String C,
                             @RequestParam("datab") String Datab,
                             @RequestParam("porta") String porta)throws Exception{
        return dataSharingService.jiechipt(A,B,C,Datab,porta);
    }

    @GetMapping("lubang3")
    public Lubang3 lubang3()throws Exception{
        return new Lubang3();
    }

    @GetMapping("lubang1_shuju")
    public Lubang1_shuju lubang1Shuju(@RequestParam("ldata") String Data,
                                      @RequestParam("sta1") String sta1)throws Exception{
        return dataSharingService.lubang1Shuju(Data,sta1);
    }

    @GetMapping("lubang2_shuju")
    public Lubang2_shuju lubang2Shuju(@RequestParam("ldata") String Data,
                                      @RequestParam("sta1") String sta1)throws Exception{
        return dataSharingService.lubang2Shuju(Data,sta1);
    }


    @GetMapping("lubang3_shuju")
    public Lubang3_shuju lubang3Shuju(@RequestParam("ldata") String Data,
                                      @RequestParam("sta1") String sta1,
                                      @RequestParam("sta2") String sta2)throws Exception{
        return dataSharingService.lubang3Shuju(Data,sta1,sta2);
    }


}
