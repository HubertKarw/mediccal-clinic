package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserJpaServiceTest {
    UserJpaRepository userJpaRepository;
    UserJpaService userJpaService;

    @BeforeEach
    void setup() {
        this.userJpaRepository = Mockito.mock(UserJpaRepository.class);
        this.userJpaService = new UserJpaService(userJpaRepository);
    }

    @Test
    void getUsers_usersExists_UsersReturned() {
        //given
        User user1 = new User(1L, "123", "321");
        User user2 = new User(2L, "123", "321");
        User user3 = new User(3L, "123", "321");
        PageImpl<User> page = new PageImpl<>(List.of(user1, user2, user3));
        when(userJpaRepository.findAll(any(Pageable.class))).thenReturn(page);
        //when
        List<User> result = userJpaService.getUsers(PageRequest.of(0, 3));
        //then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals(3L, result.get(2).getId()),
                () -> assertEquals("123", result.get(0).getUsername()),
                () -> assertEquals("321", result.get(0).getPassword())
        );
    }

    @Test
    void getUser_userExists_userReturned() {
        //given
        User user1 = new User(1L, "123", "321");
        when(userJpaRepository.findByUsername("123")).thenReturn(Optional.of(user1));
        //when
        User result = userJpaService.getUser("123");
        //then
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("123", result.getUsername()),
                () -> assertEquals("321", result.getPassword())
        );

    }

    @Test
    void addUser_validUser_userAdded(){
        //given
        User user = new User(1L, "123", "321");
        when(userJpaRepository.save(any())).thenReturn(user);
        //when
        User result = userJpaService.addUser(user);
        //then
        assertEquals(1L,result.getId());
        verify(userJpaRepository).save(user);
    }

    @Test
    void removeUser_userExists_userRemoved(){
        //given
        User user = new User(1L, "123", "321");
        when(userJpaRepository.findByUsername("123")).thenReturn(Optional.of(user));
        //when
        userJpaService.removeUser("123");
       //then
       verify(userJpaRepository).delete(user);
    }

    @Test
    void modifyUser_userExists_userModified(){
        User user = new User(1L, "123", "321");
        User modifiedUser = new User(1L, "1234", "4321");
        when(userJpaRepository.findByUsername("123")).thenReturn(Optional.of(user));
        //when
        User result = userJpaService.modifyUser("123",modifiedUser);
        //then
        verify(userJpaRepository).save(user);
        assertEquals(user.getId(),modifiedUser.getId());
    }

}
