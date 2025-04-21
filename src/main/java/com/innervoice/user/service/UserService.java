package com.innervoice.user.service;

import com.innervoice.user.domain.User;
import com.innervoice.user.dto.request.UserCreateRequest;
import com.innervoice.user.dto.response.UserCreateResponse;
import com.innervoice.user.exception.UserException;
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
}
