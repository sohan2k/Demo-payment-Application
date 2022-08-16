package io.sohan.Springbootpaymentgateway.controller;

import com.google.gson.Gson;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.dto.request.RefundRequestDto;
import io.sohan.Springbootpaymentgateway.service.RefundServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("refund")
public class RefundContoller {

    private static final Gson gson = new Gson();
    private RefundServices refundServices;

    public RefundContoller(RefundServices refundServices) {
        this.refundServices = refundServices;
    }

    @PostMapping(value = "/pay")
    @ResponseBody
    public String addrefund(@RequestBody RefundRequestDto refundRequestDto) throws RazorpayException {

        return gson.toJson(refundServices.add(refundRequestDto));
    }
}
