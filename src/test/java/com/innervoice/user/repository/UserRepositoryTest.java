package com.innervoice.user.repository;

import com.innervoice.user.domain.Role;
import com.innervoice.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("save() 호출 후 저장된 User 객체를 반환한다.")
    void save_user_test(){
        //given
        User user = User.builder()
                .name("user1")
                .password("password1")
                .role(Role.PARENT)
                .build();
        //when
        User savedUser = userRepository.save(user);

        //then
        assertNotNull(savedUser);
        assertTrue(user.getName().equals(savedUser.getName()));
        assertTrue(user.getPassword().equals(savedUser.getPassword()));
        assertTrue(user.getRole().equals(savedUser.getRole()));
    }

    @Test
    @DisplayName("존재하지 않은 이름으로 findByName() 호출 시 Optional.empty()를 반환한다")
    void find_by_name_not_found(){
        Optional<User> result = userRepository.findByName("unknown");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("여러 유저 저장 후 findAll() 조회하면 저장한 유저 수만큼 반환된다")
    void save_multiple_and_find_all(){
        //given
        User user1 = User.builder()
                .name("user1")
                .password("password1")
                .role(Role.PARENT)
                .build();
        User user2 = User.builder()
                .name("user2")
                .password("password2")
                .role(Role.PARENT)
                .build();
        userRepository.saveAll(List.of(user1, user2));
        //when
        List<User> users = userRepository.findAll();

        //then
        assertThat(users).hasSize(2)
                .extracting(User::getName)
                .contains("user1", "user2");
    }

    @Test
    @DisplayName("delete() 호출 이후에는 해당 유저가 조회되지 않는다")
    void delete_by_id_test(){
        //given
        User user = User.builder()
                .name("user1")
                .password("password1")
                .role(Role.PARENT)
                .build();
        userRepository.save(user);

        //when
        userRepository.deleteById(user.getId());
        Optional<User> result = userRepository.findByName(user.getName());

        //then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("동일 이름으로 두 번째 저장 시 Unique 제약에 의해 예외가 발생한다")
    void duplicate_name_throws_exception(){
        //given
        User user1 = User.builder()
                .name("duplicateName")
                .password("password1")
                .role(Role.PARENT)
                .build();

        userRepository.save(user1);
        //when
        User user2 = User.builder()
                .name("duplicateName")
                .password("password2")
                .role(Role.CHILD)
                .build();

        //then
        assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("constraint");
    }
}