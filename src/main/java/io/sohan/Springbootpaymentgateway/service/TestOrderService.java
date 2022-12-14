//package io.sohan.paymentgateway.service;
//
//import com.google.gson.Gson;
//import com.razorpay.Order;
//import com.razorpay.Payment;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
//import io.sohan.paymentgateway.dto.OrderCheckDto;
//import io.sohan.paymentgateway.dto.OrderDto;
//import io.sohan.paymentgateway.model.Orders;
//import io.sohan.paymentgateway.model.Payments;
//import io.sohan.paymentgateway.repository.OrderRepository;
//import io.sohan.paymentgateway.repository.PaymentRepository;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//@Service
//public class OrderService {
//    private final OrderRepository orderRepository;
//    private final PaymentRepository paymentRepository;
//    @Value("${Razorpay.keyId}")
//    private String key_id;
//    @Value("${Razorpay.Secret-key}")
//    private String key_secret;
//
//
//    public OrderService(OrderRepository orderRepository, PaymentRepository paymentRepository) {
//        this.orderRepository = orderRepository;
//        this.paymentRepository = paymentRepository;
//    }
//
//    public void createOrder(@RequestBody OrderDto orderDto){
//        try {
//            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
//            JSONObject orderRequest = new JSONObject();
//            int amt=Integer.parseInt(orderDto.getAmount());
//            orderRequest.put("amount", amt*100);
//            orderRequest.put("receipt", "order_receiptId_"+Math.random()*(9999-1000)+1000);
//            orderRequest.put("currency", "INR");
//
//            Order order=razorpayClient.Orders.create(orderRequest);
//            save(order);
//            System.out.println(order);
//        }catch (RazorpayException e){
//            e.printStackTrace();
//
//        }
//    }
//
//    public Orders checkout(@RequestBody OrderDto orderDto){
//        try {
//            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
//            JSONObject orderRequest = new JSONObject();
//            int amnt=Integer.parseInt(orderDto.getAmount());
//            orderRequest.put("amount", amnt*100);
//            orderRequest.put("receipt", "order_receiptId_"+Math.random()*(9999-1000)+1000);
//            orderRequest.put("currency", "INR");
//
//            Order order=razorpayClient.Orders.create(orderRequest);
////            System.out.println(order);
//            return save(order);
//        }catch (RazorpayException e){
//            e.printStackTrace();
//
//        }
//        return null;
//    }
//    public void checkPayment(OrderCheckDto orderCheckDto, String id) throws RazorpayException {
//        //orderRepository.save(orders);
//        Gson gson=new Gson();
//
//        RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);
//
//        String paymentId = orderCheckDto.getRazorpay_payment_id();
//
//        Payment payment = razorpay.Payments.fetch(paymentId);
//        Payments payments=gson.fromJson(payment.toString(),Payments.class);
//        System.out.println(payments);
//        if(payment.get("status").equals("captured")){
//            Orders orders=getOrder(orderCheckDto.getRazorpay_order_id());
//            orders.setRazorpay_payment_id(paymentId);
//            orders.setRazorpay_signature(orderCheckDto.getRazorpay_signature());
//            orderRepository.save(orders);
//            paymentRepository.save(payments);
//            System.out.println("success");
//        }else {
//            System.out.println("failed");
//        }
//    }
//
//
//    public Orders getOrder(String id){
//        try{
//            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
//            Order order=razorpayClient.Orders.fetch(id);
//            return save(order);
//        }catch (RazorpayException e){
//            e.printStackTrace();
//
//        }
//        return null;
//    }
//
//
//    public Orders save(Order order){
//        Gson gson=new Gson();
//        Orders orders=gson.fromJson(order.toString(),Orders.class);
//        if(!orderRepository.existsById(orders.getId())){
//            orders=orderRepository.save(orders);
//        }
//        else if(orderRepository.existsById(orders.getId()) && orders.getStatus()!="created"){
//            Orders orders1=orderRepository.getById(orders.getId());
//            orders1.setStatus(orders.getStatus());
//            orders=orderRepository.save(orders1);
//        }
//        System.out.println(orders);
//        return orders;
//    }
//
//}
