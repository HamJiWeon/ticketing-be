package hello.ticketing.service;

import hello.ticketing.dto.request.UserCreateRequest;
import hello.ticketing.dto.request.UserUpdateRequest;
import hello.ticketing.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse create(UserCreateRequest request);

    Page<UserResponse> findAll(Pageable pageable);

    UserResponse findById(Long id);

    UserResponse update(Long id, UserUpdateRequest request);

    void delete(Long id);
}
