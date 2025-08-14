package PaymentManagementSystem.service;


import PaymentManagementSystem.DTO.request.UserRequest;
import PaymentManagementSystem.DTO.response.UserResponse;
import PaymentManagementSystem.entity.User;
import PaymentManagementSystem.enums.UserRole;
import PaymentManagementSystem.exception.UserNotFoundException;
import PaymentManagementSystem.repository.UserRepository;
import PaymentManagementSystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User("John Doe", "john@example.com", "encodedPassword", UserRole.ADMIN);
        testUser.setId(1L);

        userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john@example.com");
        userRequest.setPassword("password123");
        userRequest.setRole(UserRole.ADMIN);
    }

    @Test
    void createUser_Success() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserResponse result = userService.createUser(userRequest);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals(UserRole.ADMIN, result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getAllUsers_Success() {
        // Given
        User user2 = new User("Jane Doe", "jane@example.com", "password", UserRole.VIEWER);
        user2.setId(2L);
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, user2));

        // When
        List<UserResponse> result = userService.getAllUsers();

        // Then
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
    }

    @Test
    void getUserById_NotFound_ThrowsException() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }
}