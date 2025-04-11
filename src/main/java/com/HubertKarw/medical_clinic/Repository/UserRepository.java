package com.HubertKarw.medical_clinic.Repository;

import com.HubertKarw.medical_clinic.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final List<User> users;

    public List<User> getUsers(){
        return new ArrayList<>(users);
    }
    public Optional<User> findByUsername(String username){
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }
    public User addUser(User user){
        users.add(user);
        return user;
    }
    public void removeUser(String username){
        User user = findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("no user found with this username"));
        users.remove(user);
    }
    public User modifyUser(String username, User newUser){
        User user = findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("no user found with this username"));
        user.update(newUser);
        return user;
    }

}
