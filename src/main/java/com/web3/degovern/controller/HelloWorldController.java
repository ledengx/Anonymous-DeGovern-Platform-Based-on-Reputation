package com.web3.degovern.controller;


import com.web3.degovern.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloWorldController {
    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping("set")
    public String set(@RequestParam("n") String n) throws Exception {
        System.out.println("n = " + n);
        return helloWorldService.set(n);
    }

    @GetMapping("get")
    public String get() throws Exception {
        return helloWorldService.get();
    }
}
