package io.sohan.Springbootpaymentgateway.service.impl;

import com.google.gson.Gson;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.Conveter.CustomerConverter;
import io.sohan.Springbootpaymentgateway.Conveter.PaymentConverter;
import io.sohan.Springbootpaymentgateway.dto.request.OrderCheckDto;
import io.sohan.Springbootpaymentgateway.dto.response.PaymentDto;
import io.sohan.Springbootpaymentgateway.model.Customers;
import io.sohan.Springbootpaymentgateway.model.Orders;
import io.sohan.Springbootpaymentgateway.model.PaymentMethod;
import io.sohan.Springbootpaymentgateway.model.Payments;
import io.sohan.Springbootpaymentgateway.repository.OrderRepository;
import io.sohan.Springbootpaymentgateway.repository.PaymentRepository;
import io.sohan.Springbootpaymentgateway.service.CustomerServices;
import io.sohan.Springbootpaymentgateway.service.PaymentServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentServicesImpl implements PaymentServices {

    private static final Gson gson = new Gson();
    private final PaymentRepository paymentRepository;
    private final PaymentConverter paymentConverter;
    private OrderRepository orderRepository;

    private CustomerServices customerServices;
    private CustomerConverter customerConverter;
    private PaymentMethodServiceImpl paymentMethodService;
    private CardServiceImpl cardService;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    public PaymentServicesImpl(PaymentRepository paymentRepository, PaymentConverter paymentConverter, OrderRepository orderRepository, CustomerServices customerServices, CustomerConverter customerConverter, PaymentMethodServiceImpl paymentMethodService, CardServiceImpl cardService) {
        this.paymentRepository = paymentRepository;
        this.paymentConverter = paymentConverter;
        this.orderRepository = orderRepository;
        this.customerServices = customerServices;
        this.customerConverter = customerConverter;
        this.paymentMethodService = paymentMethodService;
        this.cardService = cardService;
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
        if (!Objects.equals(payments.getVpa(), "success@razorpay")) {
            addTansactionDetails(orderRepository.getReferenceById(oId).getCustomers().getId(), payments);
        }
        return payments;
    }


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

    private void addTansactionDetails(Long id, Payments payments) throws RazorpayException {
        Customers customers = customerConverter.CustomerResponseDtoToCustomers(customerServices.getById(id));
//        Customers customers = customerServices.getByContact(phNo);
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setMethod(payments.getMethod());
        paymentMethod.setBank(payments.getBank());
        paymentMethod.setCardId(payments.getCard_id());
        paymentMethod.setVpa(payments.getVpa());
        paymentMethod.setWallet(payments.getWallet());
        if (payments.getCard_id() != null) {
            cardService.addCard(payments.getCard_id(), customers);
        }
        paymentMethodService.addPaymentMethod(paymentMethod, customers);

    }
}
