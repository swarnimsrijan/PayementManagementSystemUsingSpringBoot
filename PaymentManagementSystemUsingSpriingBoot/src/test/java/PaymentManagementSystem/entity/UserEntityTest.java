package PaymentManagementSystem.entity;

import PaymentManagementSystem.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserEntityTest {

    @Test
    void createUser_Success() {
        User user = new User("John Doe", "john@example.com", "password", UserRole.ADMIN);

        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    void userBuilder_Success() {
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        user.setPassword("password123");
        user.setRole(UserRole.VIEWER);

        assertEquals("Jane Doe", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals(UserRole.VIEWER, user.getRole());
    }
}