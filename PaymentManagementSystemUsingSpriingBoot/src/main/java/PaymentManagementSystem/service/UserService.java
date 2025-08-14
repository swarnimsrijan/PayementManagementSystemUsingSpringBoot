package PaymentManagementSystem.service;

import PaymentManagementSystem.DTO.request.UserRequest;
import PaymentManagementSystem.DTO.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
}