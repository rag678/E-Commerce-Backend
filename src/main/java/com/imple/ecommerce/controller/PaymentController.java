package com.imple.ecommerce.controller;

import com.imple.ecommerce.exception.OrderException;
import com.imple.ecommerce.model.Order;
import com.imple.ecommerce.repository.Orderrepository;
import com.imple.ecommerce.response.PaymentLinkResponse;
import com.imple.ecommerce.service.OrderService;
import com.imple.ecommerce.service.UserService;
import com.imple.ecommerce.utils.OrderStatus;
import com.imple.ecommerce.utils.PaymentStatus;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${razorpay.api.key}")
    String apiKey;
    @Value("${razorpay.api.secret}")
    String apiSecret;
    @Autowired
    private OrderService orderService;
    @Autowired
    private Orderrepository orderrepository;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
        Order order = orderService.findOrderById(orderId);

        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();

            paymentLinkRequest.put("amount",order.getTotalPrice()*100);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer = new JSONObject();
            customer.put("name",order.getUser().getFirstName());
            customer.put("email",order.getUser().getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("callback_url","http://localhost:3000/payment/" + orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse response = PaymentLinkResponse.builder()
                    .getPayment_link_id(paymentLinkId)
                    .payment_link_url(paymentLinkUrl)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch (Exception e){
            throw new RazorpayException(e.getMessage());
        }

    }

    @GetMapping("/payments")
    public ResponseEntity<Map<String,Object>> redirect(@RequestParam(name = "payment_id") String paymentId,@RequestParam(name = "order_id")
                                      Long orderId) throws OrderException, RazorpayException {
            Order order = orderService.findOrderById(orderId);
            RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecret);
        try {
            Payment payment = razorpay.payments.fetch(paymentId);

            if (payment.get("status").equals("captured")){
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
                order.setOrderStatus(OrderStatus.PLACED);
                orderrepository.save(order);
            }
            Map<String,Object> apiRes = new HashMap<>();
            apiRes.put("message","your order get placed");
            apiRes.put("status",true);
            return new ResponseEntity<>(apiRes,HttpStatus.ACCEPTED);

        }
        catch (Exception e){
            throw new RazorpayException(e.getMessage());
        }
    }
}
