package PaymentManagementSystem.DTO.request;



import PaymentManagementSystem.enums.PaymentCategory;
import PaymentManagementSystem.enums.PaymentStatus;
import PaymentManagementSystem.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PaymentRequest {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payment type is required")
    private PaymentType paymentType;

    @NotNull(message = "Category is required")
    private PaymentCategory category;

    @NotNull(message = "Status is required")
    private PaymentStatus status;

    // Getters and Setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }

    public PaymentCategory getCategory() { return category; }
    public void setCategory(PaymentCategory category) { this.category = category; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}
