package PaymentManagementSystem.DTO;

import PaymentManagementSystem.DTO.request.PaymentRequest;
import PaymentManagementSystem.enums.PaymentStatus;
import PaymentManagementSystem.enums.PaymentType;
import PaymentManagementSystem.enums.PaymentCategory;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

class PaymentRequestTest {

    @Test
    void createPaymentRequest_Success() {
        PaymentRequest request = new PaymentRequest();
        request.setAmount(new BigDecimal("500.00"));
        request.setPaymentType(PaymentType.INCOMING);
        request.setCategory(PaymentCategory.SALARY); // Use existing enum value
        request.setStatus(PaymentStatus.COMPLETED);

        assertEquals(new BigDecimal("500.00"), request.getAmount());
        assertEquals(PaymentType.INCOMING, request.getPaymentType());
        assertEquals(PaymentCategory.SALARY, request.getCategory()); // Update assertion
        assertEquals(PaymentStatus.COMPLETED, request.getStatus());
    }
}