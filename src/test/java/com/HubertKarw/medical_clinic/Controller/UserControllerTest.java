package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreateUserCommand;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Service.UserJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    UserJpaService userService;

    @Test
    void shouldGetUsers() throws Exception {
        List<User> users = List.of(
                new User(1L, "1", "1"),
                new User(2L, "1", "2"),
                new User(3L, "1", "3")
        );
        when(userService.getUsers(any())).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].username").value("1"))
                .andExpect(jsonPath("$[1].username").value("1"))
                .andExpect(jsonPath("$[2].username").value("1"));
    }

    @Test
    void shouldGetUserByUsername() throws Exception {
        User user = new User(1L, "1", "2");
        when(userService.getUser("1")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("1"));
    }

    @Test
    void shouldCrateUser() throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand( "1", "2");
        User user = new User(1L, "1", "2");
        when(userService.addUser(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createUserCommand)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("1"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldModifyUser() throws Exception {
        User user = new User(1L, "1", "2");
        User modifiedUser = new User(1L, "2", "3");
        CreateUserCommand modifiedCommand = new CreateUserCommand("2","3");
        when(userService.modifyUser(eq("1"),any())).thenReturn(modifiedUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(modifiedCommand))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("2"));

    }
}
