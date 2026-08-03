package hello.ticketing.service;

import hello.ticketing.domain.User;
import hello.ticketing.dto.request.UserCreateRequest;
import hello.ticketing.dto.request.UserUpdateRequest;
import hello.ticketing.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(UserCreateRequest request);

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse update(Long id, UserUpdateRequest request);

    void delete(Long id);
}