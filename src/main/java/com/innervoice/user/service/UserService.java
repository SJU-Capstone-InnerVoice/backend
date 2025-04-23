package com.innervoice.user.service;

import com.innervoice.user.domain.User;
import com.innervoice.user.dto.request.UserCreateRequest;
import com.innervoice.user.dto.request.UserLoginRequest;
import com.innervoice.user.dto.response.UserCreateResponse;
import com.innervoice.user.dto.response.UserLoginResponse;
import com.innervoice.user.exception.UserException;
import com.innervoice.user.exception.UserExceptionType;
import com.innervoice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.innervoice.user.exception.UserExceptionType.ALREADY_EXIST_NAME;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserCreateResponse onboard(UserCreateRequest request) {
        if(userRepository.findByName(request.name()).isPresent()){
            throw new UserException(ALREADY_EXIST_NAME);
        }
        User user = userRepository.save(new User(request.name(), request.password(), request.role()));
        return UserCreateResponse.of(user.getId());
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByName(request.name())
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
        if(!user.getPassword().equals(request.password())){
            throw new UserException(UserExceptionType.INVALID_PASSWORD);
        }
        return UserLoginResponse.of(user.getId(), user.getName(), user.getRole().toString());
    }
}
