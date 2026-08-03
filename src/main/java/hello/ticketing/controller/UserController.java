package hello.ticketing.controller;

import hello.ticketing.dto.request.UserCreateRequest;
import hello.ticketing.dto.request.UserUpdateRequest;
import hello.ticketing.dto.response.UserResponse;
import hello.ticketing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        UserResponse response = userService.update(userId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }
}