package io.sohan.Springbootpaymentgateway.service.impl;

import com.google.gson.Gson;
import com.razorpay.Card;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.model.Customers;
import io.sohan.Springbootpaymentgateway.model.PaymentMethod;
import io.sohan.Springbootpaymentgateway.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentMethodServiceImpl {
    private final Gson gson = new Gson();
    private PaymentMethodRepository paymentMethodRepository;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public Boolean getByVpa(String vpa, Long custId) {
        Optional<PaymentMethod> optionalPaymentMethod = paymentMethodRepository.findByVpaAndCustomersId(vpa, custId);
        if (optionalPaymentMethod.isPresent()) {
            return true;
        }
        return false;
    }

    public Boolean getByCard(String card, Long custId) {
        Optional<PaymentMethod> optionalPaymentMethod = paymentMethodRepository.findByCardIdAndCustomersId(card, custId);
        if (optionalPaymentMethod.isPresent()) {
            return true;
        }
        return false;
    }

    public void addPaymentMethod(PaymentMethod paymentMethod, Customers customers) {

        if (paymentMethod.getCardId() != null || paymentMethod.getVpa() != null) {
            if (!getByVpa(paymentMethod.getVpa(), customers.getId())) {
                paymentMethod.setCustomers(customers);
                paymentMethodRepository.save(paymentMethod);
                System.out.println(paymentMethod);
            } else if (!getByCard(paymentMethod.getCardId(), customers.getId())) {
                paymentMethod.setCustomers(customers);
                paymentMethodRepository.save(paymentMethod);
                System.out.println(paymentMethod);
            }

        }

    }

    //    public List<Object> getListCard() throws RazorpayException {
//        RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);
//
//        JSONObject params = new JSONObject();
//        params.put("expand[]", "card");
//
//        List<Payment> payment = razorpay.Payments.fetchAll(params);
//        System.out.println(payment);
//        List<Object> paymentList = new ArrayList<>();
//        for (Payment p : payment) {
//            assert false;
//            paymentList.add(p.toString());
//        }
//        System.out.println(paymentList);
//        return paymentList;
//    }
//
    public Object getCard(String id) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);

//        String paymentId = "pay_DtFYPi3IfUTgsL";

        Card card = razorpay.Cards.fetch(id);
        System.out.println(card);
        Object o = card.toString();
        System.out.println(o);
        return o;
    }


}
