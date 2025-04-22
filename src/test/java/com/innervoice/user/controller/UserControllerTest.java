package com.innervoice.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.innervoice.user.domain.Role;
import com.innervoice.user.dto.request.UserCreateRequest;
import com.innervoice.user.dto.response.UserCreateResponse;
import com.innervoice.user.exception.UserException;
import com.innervoice.user.exception.UserExceptionType;
import com.innervoice.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("onboard 요청 성공 시 UserCreateResponse 반환")
    void onboard_success() throws Exception {
        // given
        UserCreateRequest request = new UserCreateRequest("user1", "password1", Role.PARENT);
        UserCreateResponse response = new UserCreateResponse(1L);

        when(userService.onboard(any(UserCreateRequest.class))).thenReturn(response);

        // when & then
        mockMvc.perform(post("/onboard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("onboard 요청 시 이미 존재하는 이름이면 409 반환")
    void onboard_duplicate_name_throws_exception() throws Exception {
        // given
        UserCreateRequest request = new UserCreateRequest("user1", "password1", Role.PARENT);

        when(userService.onboard(any(UserCreateRequest.class)))
                .thenThrow(new UserException(UserExceptionType.ALREADY_EXIST_NAME));

        // when & then
        mockMvc.perform(post("/onboard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 존재하는 이름입니다"));
    }

    @Test
    @DisplayName("4. onboard 요청 시 잘못된 role 값이면 400 반환")
    void onboard_invalid_role_bad_request() throws Exception {
        // given
        ObjectNode invalidReq = objectMapper.createObjectNode()
                .put("name", "user2")
                .put("password", "pw2")
                .put("role", "INVALID_ROLE");

        // when & then
        mockMvc.perform(post("/onboard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReq)))
                .andExpect(status().isBadRequest());
    }
}
