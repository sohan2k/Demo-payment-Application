package io.sohan.Springbootpaymentgateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Script {
    @RequestMapping(value = "/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/checkout")
    public String getcheckout() {
        return "checkout";
    }

//    @RequestMapping(value = "/pending")
//    public String getPending() {
//        return "pending";
//    }

    @RequestMapping(value = "/customer")
    public String addCustomer() {
        return "customer";
    }

//    @RequestMapping(value = "/refund")
//    public String getRefund() {
//        return "refund";
//    }


}
