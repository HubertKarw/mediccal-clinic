package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreatePatientCommand;
import com.HubertKarw.medical_clinic.Model.CreateUserCommand;
import com.HubertKarw.medical_clinic.Model.PatientDTO;
import com.HubertKarw.medical_clinic.Model.UserDTO;
import com.HubertKarw.medical_clinic.Service.UserService;
import com.HubertKarw.medical_clinic.Service.UserStructMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "User operations")
public class UserController {

    private final UserService userService;
    private final UserStructMapper mapper;
    @Operation(summary = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))})})
    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.getUsers()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    @Operation(summary = "Get User by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user", content = {@Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{username}")
    public  UserDTO getUser(@PathVariable("username") String username){
        return mapper.toDto(userService.getUser(username));
    }
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created user", content = {@Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))}),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO addUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreateUserCommand.class),
                    examples = @ExampleObject(value = "{ \"username\": \"user123\", \"password\": \"password\"}")))
            @RequestBody CreateUserCommand command){
        return mapper.toDto(userService.addUser(mapper.toUser(command)));
    }
    @Operation(summary = "delete User by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user", content = {@Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("username") String username){
        userService.removeUser(username);
    }
    @Operation(summary = "modify User by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user", content = {@Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/{username}")
    public UserDTO modifyUser(@PathVariable("username")String username,
                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                      description = "modified user", required = true,
                                      content = @Content(mediaType = "application/json",
                                              schema = @Schema(implementation = CreateUserCommand.class),
                                              examples = @ExampleObject(value = "{ \"username\": \"user123\", \"password\": \"password\"}")))
                              @RequestBody CreateUserCommand command){
        return mapper.toDto(userService.modifyUser(username,mapper.toUser(command)));
    }
}
