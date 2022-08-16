package io.sohan.Springbootpaymentgateway.service.impl;

import com.google.gson.Gson;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.Conveter.PaymentConverter;
import io.sohan.Springbootpaymentgateway.dto.request.OrderCheckDto;
import io.sohan.Springbootpaymentgateway.dto.response.PaymentDto;
import io.sohan.Springbootpaymentgateway.model.Orders;
import io.sohan.Springbootpaymentgateway.model.Payments;
import io.sohan.Springbootpaymentgateway.repository.OrderRepository;
import io.sohan.Springbootpaymentgateway.repository.PaymentRepository;
import io.sohan.Springbootpaymentgateway.service.PaymentServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServicesImpl implements PaymentServices {

    private static final Gson gson = new Gson();
    private final PaymentRepository paymentRepository;
    private final PaymentConverter paymentConverter;
    private OrderRepository orderRepository;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    public PaymentServicesImpl(PaymentRepository paymentRepository, PaymentConverter paymentConverter, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentConverter = paymentConverter;

        this.orderRepository = orderRepository;
    }

    @Override
    public List<PaymentDto> getAll(Long customerId) {
        return null;
    }

    @Override
    public PaymentDto getById(String id) throws RazorpayException {
        Optional<Payments> optionalPayments = paymentRepository.findById(id);

        if (optionalPayments.isEmpty()) {
            throw new RuntimeException("No data available!");
        }
        update(id);

        return paymentConverter.paymentsToPaymentDto(optionalPayments.get());
    }

    @Override
    public Payments add(OrderCheckDto orderCheckDto) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
        Payment payment = razorpayClient.Payments.fetch(orderCheckDto.getRazorpay_payment_id());
        String oId = payment.get("order_id");
        System.out.println(oId);
        Payments payments = savePayment(payment);
        System.out.println(orderRepository.getReferenceById(oId));
        System.out.println(payments);
        payments.setOrders(orderRepository.getReferenceById(oId));
        paymentRepository.save(payments);
        return payments;
    }

//    @Override
//    public PaymentDto add(OrderCheckDto orderCheckDto, Orders orders) throws RazorpayException {
//        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
//        Payment payment = razorpayClient.Payments.fetch(orderCheckDto.getRazorpay_payment_id());
//        Payments payments = savePayment(payment);
//        payments.setOrders(orders);
//        payments = paymentRepository.save(payments);
//        return paymentConverter.paymentsToPaymentDto(payments);
//    }

//    @Override
//    public PaymentDto add(OrderCheckDto orderCheckDto) throws RazorpayException {
//        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
//        Payment payment = razorpayClient.Payments.fetch(orderCheckDto.getRazorpay_payment_id());
//        Payments payments = savePayment(payment);
//        System.out.println(payments);
//        if (payment.get("status").equals("captured")) {
//            System.out.println("in If ");
//            Orders orders = orderServices.update(orderCheckDto);
//            payments.setOrders(orders);
//            System.out.println(payments);
//        }
//        payments = paymentRepository.save(payments);
//        return paymentConverter.paymentsToPaymentDto(payments);
//    }

    @Override
    public PaymentDto update(String paymentId) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
        Payment payment = razorpayClient.Payments.fetch(paymentId);
        PaymentDto paymentDto = paymentConverter.paymentsToPaymentDto(gson.fromJson(payment.toString(), Payments.class));
        Payments payments = paymentRepository.getReferenceById(paymentId);
        payments = paymentConverter.toUpdatePayments(paymentDto, payments);
        paymentRepository.save(payments);
        if (payments.getRefund_status() != null) {
            Orders orders = orderRepository.findByPaymentsIdAndId(paymentId, payment.get("order_id")).get();
            orders.setRefund_status(payments.getRefund_status());
            orderRepository.save(orders);
        }
        return paymentConverter.paymentsToPaymentDto(payments);
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Payments savePayment(Payment payment) {
        Payments payments = gson.fromJson(payment.toString(), Payments.class);
        payments = paymentRepository.save(payments);
        return payments;
    }
}
