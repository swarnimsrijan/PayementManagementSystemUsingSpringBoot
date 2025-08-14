package PaymentManagementSystem.controller;

import PaymentManagementSystem.DTO.request.PaymentRequest;
import PaymentManagementSystem.DTO.response.ApiResponse;
import PaymentManagementSystem.DTO.response.PaymentResponse;
import PaymentManagementSystem.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody PaymentRequest paymentRequest, Authentication authentication) {
        PaymentResponse paymentResponse = paymentService.createPayment(paymentRequest, authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Payment created successfully", paymentResponse));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER', 'VIEWER')")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER', 'VIEWER')")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id) {
        PaymentResponse paymentResponse = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success("Payment retrieved successfully", paymentResponse));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(
            @PathVariable Long id, @Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = paymentService.updatePayment(id, paymentRequest);
        return ResponseEntity.ok(ApiResponse.success("Payment updated successfully", paymentResponse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(ApiResponse.success("Payment deleted successfully", "Payment with ID " + id + " has been deleted"));
    }
}