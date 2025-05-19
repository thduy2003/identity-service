package com.thduy2003.identity_service.controller;

import com.thduy2003.identity_service.dto.response.ApiResponse;
import com.thduy2003.identity_service.dto.request.UserCreationRequest;
import com.thduy2003.identity_service.dto.request.UserUpdateRequest;
import com.thduy2003.identity_service.dto.response.UserResponse;
import com.thduy2003.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        log.info("Controller: create user");

        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));

        return apiResponse;
    }
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.getUsers());
        return apiResponse;
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.getUser(userId));
        return apiResponse;
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo () {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.updateUser(userId, request));
        return apiResponse;
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Object> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        ApiResponse<Object> apiResponse = new ApiResponse<>();

        apiResponse.setMessage("User has been deleted");
        return apiResponse;
    }
}
