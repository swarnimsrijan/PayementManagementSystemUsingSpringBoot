package PaymentManagementSystem.service.impl;

import PaymentManagementSystem.DTO.request.PaymentRequest;
import PaymentManagementSystem.DTO.response.PaymentResponse;
import PaymentManagementSystem.entity.Payment;
import PaymentManagementSystem.entity.User;
import PaymentManagementSystem.exception.PaymentNotFoundException;
import PaymentManagementSystem.exception.UserNotFoundException;
import PaymentManagementSystem.repository.PaymentRepository;
import PaymentManagementSystem.repository.UserRepository;
import PaymentManagementSystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userEmail));

        Payment payment = new Payment(
                paymentRequest.getAmount(),
                paymentRequest.getPaymentType(),
                paymentRequest.getCategory(),
                paymentRequest.getStatus(),
                LocalDateTime.now(),
                user
        );

        Payment savedPayment = paymentRepository.save(payment);
        return mapToResponse(savedPayment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        return mapToResponse(payment);
    }

    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentType(paymentRequest.getPaymentType());
        payment.setCategory(paymentRequest.getCategory());
        payment.setStatus(paymentRequest.getStatus());

        Payment updatedPayment = paymentRepository.save(payment);
        return mapToResponse(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentType(),
                payment.getCategory(),
                payment.getStatus(),
                payment.getDate(),
                payment.getCreatedBy().getName()
        );
    }
}