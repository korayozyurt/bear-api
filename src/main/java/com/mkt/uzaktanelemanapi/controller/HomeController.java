package com.mkt.uzaktanelemanapi.controller;

import com.mkt.uzaktanelemanapi.tools.BEAR;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String uzaktanEleman(){
        return "uzaktaneleman" + BEAR.version;
    }

}
