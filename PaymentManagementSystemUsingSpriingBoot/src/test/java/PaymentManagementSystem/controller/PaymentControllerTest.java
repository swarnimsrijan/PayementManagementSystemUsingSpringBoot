package PaymentManagementSystem.controller;

import PaymentManagementSystem.DTO.request.PaymentRequest;
import PaymentManagementSystem.DTO.response.PaymentResponse;
import PaymentManagementSystem.enums.PaymentCategory;
import PaymentManagementSystem.enums.PaymentStatus;
import PaymentManagementSystem.enums.PaymentType;
import PaymentManagementSystem.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentRequest paymentRequest;
    private PaymentResponse paymentResponse;

    @BeforeEach
    void setUp() {
        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(new BigDecimal("1000.00"));
        paymentRequest.setPaymentType(PaymentType.OUTGOING);
        paymentRequest.setCategory(PaymentCategory.SALARY);
        paymentRequest.setStatus(PaymentStatus.PENDING);

        paymentResponse = new PaymentResponse(1L, new BigDecimal("1000.00"), PaymentType.OUTGOING, PaymentCategory.SALARY, PaymentStatus.PENDING, null, null);
        paymentResponse.setId(1L);
        paymentResponse.setAmount(new BigDecimal("1000.00"));
        paymentResponse.setPaymentType(PaymentType.OUTGOING);
        paymentResponse.setCategory(PaymentCategory.SALARY);
        paymentResponse.setStatus(PaymentStatus.PENDING);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createPayment_Success() throws Exception {
        when(paymentService.createPayment(any(PaymentRequest.class), anyString())).thenReturn(paymentResponse);

        mockMvc.perform(post("/api/payments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.amount").value(1000.00));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllPayments_Success() throws Exception {
        List<PaymentResponse> payments = Arrays.asList(paymentResponse);
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getPaymentById_Success() throws Exception {
        when(paymentService.getPaymentById(1L)).thenReturn(paymentResponse);

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updatePayment_Success() throws Exception {
        when(paymentService.updatePayment(eq(1L), any(PaymentRequest.class))).thenReturn(paymentResponse);

        mockMvc.perform(put("/api/payments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletePayment_Success() throws Exception {
        doNothing().when(paymentService).deletePayment(1L);

        mockMvc.perform(delete("/api/payments/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}