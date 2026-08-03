package hello.ticketing.controller;

import hello.ticketing.dto.request.UserCreateRequest;
import hello.ticketing.dto.request.UserUpdateRequest;
import hello.ticketing.dto.response.UserResponse;
import hello.ticketing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody UserCreateRequest request) {
        return userService.create(request);
    }

    @PatchMapping("/{userId}")
    public UserResponse update(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        return userService.update(userId, request);
    }

    @DeleteMapping("/{userId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}