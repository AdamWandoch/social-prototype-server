package com.adamwandoch.socialprotoapi.models.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Adam Wandoch
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public Optional<UserModel> getUser(Long id) {
        return userRepository.findById(id);
    }

    public UserModel getUserByNickname(String nickname) {
        return userRepository.findFirstByNickname(nickname);
    }

    public void saveUser(UserModel user) {
        if (!userRepository.existsByNickname(user.getNickname())) {
            userRepository.save(user);
        } else {
            UserModel existingUser = userRepository.findFirstByNickname(user.getNickname());
            if (existingUser != null) {
                existingUser.setAvatarId(user.getAvatarId());
                userRepository.save(existingUser);
            }
        }
    }
}
