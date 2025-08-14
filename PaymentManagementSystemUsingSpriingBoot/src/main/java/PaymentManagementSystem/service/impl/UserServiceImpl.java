package PaymentManagementSystem.service.impl;

import PaymentManagementSystem.DTO.request.UserRequest;
import PaymentManagementSystem.DTO.response.UserResponse;
import PaymentManagementSystem.entity.User;
import PaymentManagementSystem.exception.UserNotFoundException;
import PaymentManagementSystem.repository.UserRepository;
import PaymentManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(
                userRequest.getName(),
                userRequest.getEmail(),
                passwordEncoder.encode(userRequest.getPassword()),
                userRequest.getRole()
        );

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getName(),
                savedUser.getEmail(), savedUser.getRole());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getName(),
                        user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return new UserResponse(user.getId(), user.getName(),
                user.getEmail(), user.getRole());
    }
}
