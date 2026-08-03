package hello.ticketing.service;

import hello.ticketing.domain.User;
import hello.ticketing.dto.request.UserCreateRequest;
import hello.ticketing.dto.request.UserUpdateRequest;
import hello.ticketing.global.exception.DuplicateEmailException;
import hello.ticketing.global.exception.UserNotFoundException;
import hello.ticketing.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void 회원가입_성공() {
        UserCreateRequest request = new UserCreateRequest(
                "홍길동",
                "1234",
                "test@test.com",
                "01012345678",
                20,
                "서울"
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = userService.create(request);

        assertThat(response.name()).isEqualTo("홍길동");
        assertThat(response.email()).isEqualTo("test@test.com");
        assertThat(response.phone()).isEqualTo("01012345678");
        assertThat(response.age()).isEqualTo(20);
        assertThat(response.address()).isEqualTo("서울");

        verify(passwordEncoder).encode("1234");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 이미_존재하는_이메일이면_회원가입_실패() {
        UserCreateRequest request = new UserCreateRequest(
                "홍길동",
                "1234",
                "test@test.com",
                "01012345678",
                20,
                "서울"
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("이미 존재하는 이메일입니다. email=test@test.com");

        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void 유저정보_수정_성공() {
        Long userId = 1L;

        User user = User.builder()
                .name("홍길동")
                .password("old-password")
                .email("test@test.com")
                .phone("01011112222")
                .age(20)
                .address("서울")
                .provider("local")
                .providerId("test@test.com")
                .build();

        UserUpdateRequest request = new UserUpdateRequest(
                "new-password",
                "01099998888",
                "부산"
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(request.password())).thenReturn("encoded-new-password");

        var response = userService.update(userId, request);

        assertThat(response.phone()).isEqualTo("01099998888");
        assertThat(response.address()).isEqualTo("부산");
        assertThat(user.getPhone()).isEqualTo("01099998888");
        assertThat(user.getPassword()).isEqualTo("encoded-new-password");

        verify(passwordEncoder).encode("new-password");
    }

    @Test
    void 없는_유저를_수정하면_예외() {
        Long userId = 1L;

        UserUpdateRequest request = new UserUpdateRequest(
                "new-password",
                "01099998888",
                "부산"
        );

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(userId, request))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void 회원탈퇴_성공() {
        Long userId = 1L;

        User user = User.builder()
                .name("홍길동")
                .password("password")
                .email("test@test.com")
                .phone("01012345678")
                .age(20)
                .address("서울")
                .provider("local")
                .providerId("test@test.com")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository).delete(user);
    }
}
