package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreateUserCommand;
import com.HubertKarw.medical_clinic.Model.UserDTO;
import com.HubertKarw.medical_clinic.Service.UserService;
import com.HubertKarw.medical_clinic.Service.UserStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserStructMapper mapper;

    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.getUsers()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public  UserDTO getUser(@PathVariable("username") String username){
        return mapper.toDto(userService.getUser(username));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@RequestBody CreateUserCommand command){
        return mapper.toDto(userService.addUser(mapper.toUser(command)));
    }
    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("username") String username){
        userService.removeUser(username);
    }
    @PutMapping("/{username}")
    public UserDTO modifyUser(@PathVariable("username")String username, @RequestBody CreateUserCommand command){
        return mapper.toDto(userService.modifyUser(username,mapper.toUser(command)));
    }
}
