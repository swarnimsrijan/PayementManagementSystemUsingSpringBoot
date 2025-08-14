package PaymentManagementSystem.DTO.response;


import PaymentManagementSystem.enums.PaymentCategory;
import PaymentManagementSystem.enums.PaymentStatus;
import PaymentManagementSystem.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {
    private Long id;
    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentCategory category;
    private PaymentStatus status;
    private LocalDateTime date;
    private String createdBy;

    public PaymentResponse(Long id, BigDecimal amount, PaymentType paymentType,
                           PaymentCategory category, PaymentStatus status,
                           LocalDateTime date, String createdBy) {
        this.id = id;
        this.amount = amount;
        this.paymentType = paymentType;
        this.category = category;
        this.status = status;
        this.date = date;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }

    public PaymentCategory getCategory() { return category; }
    public void setCategory(PaymentCategory category) { this.category = category; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
