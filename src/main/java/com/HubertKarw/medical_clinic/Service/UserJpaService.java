package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserJpaService {
    private final UserJpaRepository repository;

    public List<User> getUsers(Pageable pageable) {
        Page<User> users = repository.findAll(pageable);
        return users.getContent();
    }

    public User getUser(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("no such user"));
    }

    @Transactional
    public User addUser(User user) {
        return repository.save(user);
    }

    @Transactional
    public void removeUser(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("no such user"));
        repository.delete(user);
    }

    @Transactional
    public User modifyUser(String username, User newUser) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("no such user"));
        user.update(newUser);
        return repository.save(user);
    }
}
