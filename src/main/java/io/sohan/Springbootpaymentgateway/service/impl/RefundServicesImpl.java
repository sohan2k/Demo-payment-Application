package io.sohan.Springbootpaymentgateway.service.impl;

import com.google.gson.Gson;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import io.sohan.Springbootpaymentgateway.Conveter.PaymentConverter;
import io.sohan.Springbootpaymentgateway.Conveter.RefundConveter;
import io.sohan.Springbootpaymentgateway.dto.request.RefundRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.RefundDto;
import io.sohan.Springbootpaymentgateway.model.Refunds;
import io.sohan.Springbootpaymentgateway.repository.RefundRepository;
import io.sohan.Springbootpaymentgateway.service.PaymentServices;
import io.sohan.Springbootpaymentgateway.service.RefundServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.sohan.Springbootpaymentgateway.service.impl.OrderServicesImpl.convertRupeeToPaise;

@Service
public class RefundServicesImpl implements RefundServices {
    private static final Gson gson = new Gson();
    private RefundRepository refundRepository;
    private PaymentServices paymentServices;
    private RefundConveter refundConveter;
    private PaymentConverter paymentConverter;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    public RefundServicesImpl(RefundRepository refundRepository, PaymentServices paymentServices, RefundConveter refundConveter, PaymentConverter paymentConverter) {
        this.refundRepository = refundRepository;
        this.paymentServices = paymentServices;
        this.refundConveter = refundConveter;
        this.paymentConverter = paymentConverter;
    }

    @Override
    public List<RefundDto> getAll(Long paymentId) {
        return null;
    }

    @Override
    public RefundDto add(RefundRequestDto refundRequestDto) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);
//        String paymentId = "pay_K30a3EG6YBslf0";

        JSONObject request = new JSONObject();
        request.put("amount", convertRupeeToPaise(refundRequestDto.getAmount()));
        request.put("speed", refundRequestDto.getSpeed());
        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "Test Refund");
        request.put("notes", notes);
        Refund refund = razorpay.Payments.refund(refundRequestDto.getId(), request);
        Refunds refunds = gson.fromJson(refund.toString(), Refunds.class);
        refunds.setPayments(paymentConverter.paymentDtoToPayments(
                paymentServices.getById(refundRequestDto.getId())));
        refundRepository.save(refunds);
        System.out.println(refund);
        RefundDto refundDto = refundConveter.refundsToRefundDto(refunds);
        refundDto.setPayment_id(refunds.getPayments().getId());
        return refundDto;
    }
}
