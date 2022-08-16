package io.sohan.Springbootpaymentgateway.service.impl;

import com.google.gson.Gson;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.Conveter.CustomerConverter;
import io.sohan.Springbootpaymentgateway.Conveter.OrderConverter;
import io.sohan.Springbootpaymentgateway.dto.request.OrderCheckDto;
import io.sohan.Springbootpaymentgateway.dto.request.OrderRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.OrderResponseDto;
import io.sohan.Springbootpaymentgateway.model.Orders;
import io.sohan.Springbootpaymentgateway.repository.OrderRepository;
import io.sohan.Springbootpaymentgateway.service.CustomerServices;
import io.sohan.Springbootpaymentgateway.service.OrderServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServicesImpl implements OrderServices {
    private static final Gson gson = new Gson();
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    //    private final RazorpayClient razorpayClient;
    private final CustomerServices customerServices;
    private final CustomerConverter customerConverter;

    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    private OrderServicesImpl(OrderRepository orderRepository, OrderConverter orderConverter, CustomerServices customerServices, CustomerConverter customerConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.customerServices = customerServices;
        this.customerConverter = customerConverter;
//        this.paymentServices = paymentServices;
//        this.paymentRepository = paymentRepository;
    }

    public static String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }

    @Override
    public List<OrderResponseDto> getAll(Long customerId) {
        List<Orders> ordersList = orderRepository.findAllByCustomersId(customerId);

        if (ordersList.isEmpty()) {
            throw new RuntimeException("No data available!");
        }
        return ordersList.stream()
                .map(orderConverter::ordersToOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getById(Long customerId, String id) {
        Optional<Orders> optionalOrders = orderRepository.findByCustomersIdAndId(customerId, id);

        if (optionalOrders.isEmpty()) {
            throw new RuntimeException("No data available!");
        }
        return orderConverter.ordersToOrderResponseDto(optionalOrders.get());

    }

    @Override
    public Orders getOrder(String id) {
        Optional<Orders> optionalOrders = orderRepository.findById(id);

        if (optionalOrders.isEmpty()) {
            throw new RuntimeException("No data available!");
        }
        return optionalOrders.get();
    }

//    @Override
//    public void update(String id, OrderCheckDto orderCheckDto) throws RazorpayException {
//        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
//        Orders orders = getOrder(id);
//        Order order = razorpayClient.Orders.fetch(id);
////        if(o)
//        Orders orders1 = gson.fromJson(order.toString(), Orders.class);
//        orders.setStatus(orders1.getStatus());
//        orders.setAmount_due(orders1.getAmount_due());
//        orders.setAmount_paid(orders1.getAmount_paid());
//        orders.setAttempts(orders1.getAttempts());
//        orders.setRazorpay_payment_id(orderCheckDto.getRazorpay_payment_id());
//        orders.setRazorpay_signature(orderCheckDto.getRazorpay_signature());
////        orders.setPayments(payments);
//        System.out.println(orders);
//        orders = orderRepository.save(orders);
//        paymentServices.add(orderCheckDto, orders);
//    }

    @Override
    public OrderResponseDto add(OrderRequestDto orderRequestDto) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", convertRupeeToPaise(orderRequestDto.getAmount()));
            orderRequest.put("receipt", "order_receiptId_" + Math.random() * (9999 - 1000) + 1000);
            orderRequest.put("currency", "INR");

            Order order = razorpayClient.Orders.create(orderRequest);
            Orders orders = save(order);
//            CustomerDto customerDto = customerServices.getByContact(orderDto.getContact());
//            if (customerDto == null) {
//                customerDto = orderDtoToCustomerDto(orderDto);
//            }
            orders.setCustomers(customerServices.getByContact(orderRequestDto.getContact()));
            orders = orderRepository.save(orders);
            return orderConverter.ordersToOrderResponseDto(orders);

        } catch (RazorpayException e) {
            e.printStackTrace();
        }
        return null;
//
    }

    @Override
    public Orders update(OrderCheckDto orderCheckDto) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);

        Order order = razorpayClient.Orders.fetch(orderCheckDto.getRazorpay_order_id());
        Orders orders = save(order);
//        Orders orders1 = gson.fromJson(order.toString(), Orders.class);
//        orders.setStatus(orders1.getStatus());
//        orders.setAmount_due(orders1.getAmount_due());
//        orders.setAmount_paid(orders1.getAmount_paid());
//        orders.setAttempts(orders1.getAttempts());
        orders.setRazorpay_payment_id(orderCheckDto.getRazorpay_payment_id());
        orders.setRazorpay_signature(orderCheckDto.getRazorpay_signature());
//        Payment payment = razorpayClient.Payments.fetch(orderCheckDto.getRazorpay_payment_id());
//        if (payment.get("refund_status") != null) {
//            orders.setRefund_status(payment.get("refund_status"));
//        }

        orderRepository.save(orders);
        System.out.println(orders);
        return orders;
    }

    @Override
    public OrderResponseDto pending(String id) {
        Orders orders = getOrder(id);
        System.out.println(orders);
        return orderConverter.ordersToOrderResponseDto(orders);
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    public Orders save(Order order) {
        Orders orders = gson.fromJson(order.toString(), Orders.class);
        if (!orderRepository.existsById(orders.getId())) {
            orders = orderRepository.save(orders);
        } else if (orderRepository.existsById(orders.getId()) && orders.getStatus() != "created") {
            Orders orders1 = orderRepository.getReferenceById(orders.getId());
            orders1.setStatus(orders.getStatus());
            orders1.setAmount_due(orders.getAmount_due());
            orders1.setAmount_paid(orders.getAmount_paid());
            orders1.setAttempts(orders.getAttempts());
            orders = orderRepository.save(orders1);
        }
        System.out.println(orders);
        return orders;
    }

//    public CustomerRequestDto orderDtoToCustomerDto(OrderRequestDto orderRequestDto) {
//        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
//        customerRequestDto.setEmail(orderRequestDto.getEmail());
//        customerRequestDto.setContact(orderRequestDto.getContact());
//        customerRequestDto.setName(orderRequestDto.getName());
//        customerRequestDto = customerServices.add(customerRequestDto);
//        return customerRequestDto;
//    }
}
