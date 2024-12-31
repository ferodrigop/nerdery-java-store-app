package com.example.demo.services.stripe;

import com.example.demo.dtos.payment.PaymentIntentRequestDto;
import com.example.demo.dtos.payment.PaymentIntentResponseDto;
import com.example.demo.entities.order.Order;
import com.example.demo.entities.payment.Payment;
import com.example.demo.entities.user.User;
import com.example.demo.repositories.payment.PaymentRepository;
import com.example.demo.services.order.OrderService;
import com.example.demo.utils.IAuthenticationFacade;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StripeService {
    private final IAuthenticationFacade authenticationFacade;
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;

    @Value("${stripe.api.publishable-key}")
    private String publishableKey;

    @Value("${stripe.api.secret-key}")
    private String secretKey;

    @Value("${stripe.api.webhook-secret}")
    private String webhookSecret;

    public PaymentIntentResponseDto createPaymentIntent(PaymentIntentRequestDto paymentIntentRequestDto) throws StripeException {
        Order order = orderService.findOrderById(paymentIntentRequestDto.orderId());
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(order.getTotal().longValue())
                        .setCurrency("USD")
                        .putMetadata("user-id", user.getId().toString())
                        .putMetadata("user-email", user.getEmail())
                        .putMetadata("order-id", paymentIntentRequestDto.orderId().toString())
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        paymentRepository.save(
                Payment.builder()
                        .stripePaymentIntentId(paymentIntent.getId())
                        .amount(order.getTotal())
                        .currency("USD")
                        .status(paymentIntent.getStatus())
                        .order(order)
                        .build()
        );

        return PaymentIntentResponseDto.builder()
                .clientSecret(paymentIntent.getClientSecret())
                .build();
    }

    @Transactional
    public void handleWebhookEvent(String payload, String signature) throws Exception {
        PaymentIntent paymentIntent = parseRawWebhookEvent(payload, signature);
        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntent.getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(paymentIntent.getStatus());
        paymentRepository.save(payment);
    }

    private PaymentIntent parseRawWebhookEvent(String payload, String signature) throws Exception {
        Event event;

        // Construct the event from the payload and header signature
        try {
            event = Webhook.constructEvent(payload, signature, webhookSecret);
        } catch (SignatureVerificationException e) {
            // Handle signature verification errors
            log.error("Error verifying webhook signature: {}", e.getMessage());
            throw new Exception("Error parsing payload: " + e.getMessage());
        }

        // Deserializing the Stripe event's nested object
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Handle deserialization failure, e.g., due to API version mismatch
            log.error("Error deserializing Stripe object");
            throw new Exception("Error deserializing Stripe object");
        }

        if (event.getType().equalsIgnoreCase("payment_intent.succeeded")) {
            return (PaymentIntent) stripeObject;
        }
        return null;
    }

}
