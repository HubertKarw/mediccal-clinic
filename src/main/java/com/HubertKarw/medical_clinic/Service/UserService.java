package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("no such user"));
    }

    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    public void removeUser(String username) {
        userRepository.removeUser(username);
    }

    public User modifyUser(String username, User user) {
        return userRepository.modifyUser(username, user);
    }
}
