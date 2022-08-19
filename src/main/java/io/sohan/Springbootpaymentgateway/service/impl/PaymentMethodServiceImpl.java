package io.sohan.Springbootpaymentgateway.service.impl;

import com.google.gson.Gson;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.model.Customers;
import io.sohan.Springbootpaymentgateway.model.PaymentMethod;
import io.sohan.Springbootpaymentgateway.repository.CardRepository;
import io.sohan.Springbootpaymentgateway.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentMethodServiceImpl {
    private final Gson gson = new Gson();
    private PaymentMethodRepository paymentMethodRepository;

    private CardRepository cardRepository;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository, CardRepository cardRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.cardRepository = cardRepository;
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

    public void addPaymentMethod(PaymentMethod paymentMethod, Customers customers) throws RazorpayException {
        if (paymentMethod.getCardId() != null) {
            if (!getByCard(paymentMethod.getCardId(), customers.getId())) {
                paymentMethod.setCustomers(customers);
                paymentMethodRepository.save(paymentMethod);
                System.out.println(paymentMethod);
            }
        } else {
            if (!getByVpa(paymentMethod.getVpa(), customers.getId())) {
                paymentMethod.setCustomers(customers);
                paymentMethodRepository.save(paymentMethod);
                System.out.println(paymentMethod);
            }
        }

    }


}
