package com.adamwandoch.socialprotoapi.models.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Adam Wandoch
 */

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

    @Test
    void canGetAllUsers() {
        // when
        underTest.getAllUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    void canGetUserById() {
        // given
        Long id = 123L;

        // when
        underTest.getUser(id);

        // then
        verify(userRepository).findById(id);
    }

    @Test
    void canGetUserByNickname() {
        // given
        String nickname = "some nickname";

        // when
        underTest.getUserByNickname(nickname);

        // then
        verify(userRepository).findFirstByNickname(nickname);
    }

    @Test
    void canSaveUser() {
        // given
        UserModel user = new UserModel();

        // when
        underTest.saveUser(user);

        // then
        ArgumentCaptor<UserModel> userModelArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);

        verify(userRepository).save(userModelArgumentCaptor.capture());

        UserModel capturedUser = userModelArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canUpdateUserIfAlreadyExists() {
        // given
        given(userRepository.existsByNickname(any()))
                .willReturn(true);
        given(userRepository.findFirstByNickname(any()))
                .willReturn(new UserModel());

        // when
        underTest.saveUser(new UserModel());

        // then
        verify(userRepository).save(new UserModel());
    }
}