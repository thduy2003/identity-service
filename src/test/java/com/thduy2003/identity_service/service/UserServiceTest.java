package com.thduy2003.identity_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thduy2003.identity_service.dto.request.UserCreationRequest;
import com.thduy2003.identity_service.dto.response.UserResponse;
import com.thduy2003.identity_service.entity.User;
import com.thduy2003.identity_service.exception.AppException;
import com.thduy2003.identity_service.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
	@Autowired
	private UserService userService;

	@MockitoBean
	private UserRepository userRepository;

	private UserCreationRequest request;
	private UserResponse userResponse;
	private User user;
	private LocalDate dob;

	@BeforeEach
	void initData() {
		dob = LocalDate.of(1990, 1, 1);

		request = UserCreationRequest.builder()
				.username("john")
				.firstName("John")
				.lastName("Doe")
				.password("12345678")
				.dob(dob)
				.build();

		userResponse = UserResponse.builder()
				.id("cf0600f538b3")
				.username("john")
				.firstName("John")
				.lastName("Doe")
				.dob(dob)
				.build();

		user = User.builder()
				.id("cf0600f538b3")
				.username("john")
				.firstName("John")
				.lastName("Doe")
				.dob(dob)
				.build();
	}

	@Test
	void createUser_validRequest_success() throws Exception {
		//GIVEN
		when(userRepository.existsByUsername(anyString())).thenReturn(false);
		when(userRepository.save(any())).thenReturn(user);

		//WHEN
		var response = userService.createUser(request);


		//THEN
		Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
		Assertions.assertThat(response.getUsername()).isEqualTo("john");
	}

	@Test
	void createUser_userExisted_fail() throws Exception {
		//GIVEN
		when(userRepository.existsByUsername(anyString())).thenReturn(true);

		//WHEN
		var exception = org.junit.jupiter.api.Assertions.assertThrows(AppException.class,
				() -> userService.createUser(request));

		//THEN
		Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
	}

	@Test
	@WithMockUser(username = "john")
	void getMyInfo_valid_success() {
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

		var response = userService.getMyInfo();

		Assertions.assertThat(response.getUsername()).isEqualTo("john");
		Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
	}

	@Test
	@WithMockUser(username = "john")
	void getMyInfo_userNotFound_error() {
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

		var exception = org.junit.jupiter.api.Assertions.assertThrows(AppException.class, () -> userService.getMyInfo());

		Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
	}
}
