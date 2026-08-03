package hello.ticketing.service;

import hello.ticketing.domain.User;
import hello.ticketing.dto.request.UserCreateRequest;
import hello.ticketing.dto.request.UserUpdateRequest;
import hello.ticketing.dto.response.UserResponse;
import hello.ticketing.global.exception.DuplicateEmailException;
import hello.ticketing.global.exception.UserNotFoundException;
import hello.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        User user = User.builder()
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .age(request.age())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .provider("local")
                .providerId(request.email())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserResponse.from(user);
    }

    @Override
    public  UserResponse update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.update(
                passwordEncoder.encode(request.password()),
                request.phone(),
                request.address()
        );

        return UserResponse.from(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}
