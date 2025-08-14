package PaymentManagementSystem.service;

import PaymentManagementSystem.DTO.request.PaymentRequest;
import PaymentManagementSystem.DTO.response.PaymentResponse;
import PaymentManagementSystem.entity.Payment;
import PaymentManagementSystem.entity.User;
import PaymentManagementSystem.enums.PaymentCategory;
import PaymentManagementSystem.enums.PaymentStatus;
import PaymentManagementSystem.enums.PaymentType;
import PaymentManagementSystem.enums.UserRole;
import PaymentManagementSystem.exception.PaymentNotFoundException;
import PaymentManagementSystem.repository.PaymentRepository;
import PaymentManagementSystem.repository.UserRepository;
import PaymentManagementSystem.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private User testUser;
    private Payment testPayment;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User("John Doe", "john@example.com", "password", UserRole.ADMIN);
        testUser.setId(1L);

        testPayment = new Payment(
                new BigDecimal("1000.00"),
                PaymentType.OUTGOING,
                PaymentCategory.SALARY,
                PaymentStatus.PENDING,
                LocalDateTime.now(),
                testUser
        );
        testPayment.setId(1L);

        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(new BigDecimal("1000.00"));
        paymentRequest.setPaymentType(PaymentType.OUTGOING);
        paymentRequest.setCategory(PaymentCategory.SALARY);
        paymentRequest.setStatus(PaymentStatus.PENDING);
    }

    @Test
    void createPayment_Success() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        // When
        PaymentResponse result = paymentService.createPayment(paymentRequest, "john@example.com");

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("1000.00"), result.getAmount());
        assertEquals(PaymentType.OUTGOING, result.getPaymentType());
        assertEquals(PaymentCategory.SALARY, result.getCategory());
        assertEquals(PaymentStatus.PENDING, result.getStatus());
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void getAllPayments_Success() {
        // Given
        Payment payment2 = new Payment(
                new BigDecimal("500.00"),
                PaymentType.INCOMING,
                PaymentCategory.INVOICE,
                PaymentStatus.COMPLETED,
                LocalDateTime.now(),
                testUser
        );
        payment2.setId(2L);
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(testPayment, payment2));

        // When
        List<PaymentResponse> result = paymentService.getAllPayments();

        // Then
        assertEquals(2, result.size());
        assertEquals(new BigDecimal("1000.00"), result.get(0).getAmount());
        assertEquals(new BigDecimal("500.00"), result.get(1).getAmount());
    }

    @Test
    void getPaymentById_NotFound_ThrowsException() {
        // Given
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(PaymentNotFoundException.class, () -> paymentService.getPaymentById(1L));
    }
}
