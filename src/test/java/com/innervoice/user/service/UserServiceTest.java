package com.innervoice.user.service;

import com.innervoice.user.domain.Role;
import com.innervoice.user.domain.User;
import com.innervoice.user.dto.request.UserCreateRequest;
import com.innervoice.user.dto.response.UserCreateResponse;
import com.innervoice.user.exception.UserException;
import com.innervoice.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserCreateRequest defaultRequest;
    private User savedUser;
    private User existingUser;

    @BeforeEach
    void setUp() {
        defaultRequest = new UserCreateRequest("joon", "password1", Role.PARENT);
        savedUser = mock(User.class);
        existingUser = new User("joon", "password1", Role.PARENT);
    }

    @Test
    @DisplayName("온보드 요청 성공 시 저장된 ID를 반환한다")
    void onboardSuccess_returnsId() {
        // given
        given(userRepository.findByName(defaultRequest.name()))
                .willReturn(Optional.empty());
        given(userRepository.save(any(User.class)))
                .willReturn(savedUser);

        // when
        UserCreateResponse response = userService.onboard(defaultRequest);

        // then
        verify(userRepository).findByName(defaultRequest.name());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("온보드 요청 시 이미 존재하는 이름이면 UserException을 발생시킨다")
    void onboard_duplicate_name_throws_exception() {
        //given
        given(userRepository.findByName(defaultRequest.name()))
                .willReturn(Optional.of(existingUser));

        //when & then
        assertThatThrownBy(() -> userService.onboard(defaultRequest))
                .isInstanceOf(UserException.class);
        verify(userRepository).findByName(defaultRequest.name());
    }

    @Test
    @DisplayName("온보드 과정에서 findByName과 save 호출 순서를 검증한다")
    void onboard_call_order_verified(){
        //given
        given(userRepository.findByName(defaultRequest.name()))
                .willReturn(Optional.empty());
        given(userRepository.save(any(User.class)))
                .willReturn(savedUser);

        //when
        userService.onboard(defaultRequest);

        //then
        InOrder order = inOrder(userRepository);
        order.verify(userRepository).findByName(defaultRequest.name());
        order.verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("findByName 호출 횟수와 save 호출 횟수를 검증한다")
    void onboard_verifies_interaction_counts(){
        //given
        given(userRepository.findByName(defaultRequest.name()))
                .willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(savedUser);

        //when
        userService.onboard(defaultRequest);

        //then
        verify(userRepository, times(1)).findByName("joon");
        verify(userRepository, times(1)).save(any(User.class));
    }
}