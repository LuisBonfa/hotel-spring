package com.senior.challenge.user.service;

import com.senior.challenge.user.dto.UserDTO;
import com.senior.challenge.user.entity.User;
import com.senior.challenge.user.repository.UserRepository;
import com.senior.challenge.user.utils.Security;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// In this Class there are Unit Tests that covers:
// Service Methods working properly
// Service Methods Exceptions

@ContextConfiguration(classes = {UserServiceTest.class})
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService userRoleService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    private void setUp(){
        userService = new UserService(userRepository, userRoleService);
    }

    @Test
    void findByIdSuccessTest() {
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        User expectedUser = userService.findById(user.getId());

        Assertions.assertThat(expectedUser).isNotInstanceOf(ResponseStatusException.class);
        Assertions.assertThat(expectedUser.getDocument()).isEqualTo(expectedUser.getDocument());
    }

    @Test
    void findByIdNotFoundExceptionTest(){
        ResponseStatusException exception = Assert.assertThrows(ResponseStatusException.class, ()->{
            userService.findById(UUID.randomUUID());
        });
        HttpStatus status = exception.getStatus();
        Assertions.assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findByIdIllegalArgumentsExceptionTest(){
        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, ()->{
            userService.findById(UUID.fromString(""));
        });
        Assertions.assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void userSaveSuccessTest() {
        UserDTO user = new UserDTO();
        user.setAlias("Thiago");
        user.setDocument("1234567");
        user.setEmail("thiago@gmail.com");
        user.setName("thiago");
        user.setPassword("123456789");
        user.setPhone("5512981126586");
        user.setRoles(Arrays.asList("client"));

        User newUser = userService.save(user);
        verify(userRepository, times(1)).save(newUser);
        Assertions.assertThat(newUser.getDocument()).isEqualTo(user.getDocument());
    }

    @Test
    void userUpdateSuccessTest(){

        //Creating the user before testing
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO userDTO = new UserDTO();
        userDTO.setDocument("34656312851");

        userService.update(userDTO, user.getId());
        Assertions.assertThat(user.getDocument()).isEqualTo(userDTO.getDocument());
    }

    @Test
    void userFindByDocumentOrPhoneOrNameSuccessTest(){
        //Creating the user before testing
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findByDocumentContainingOrPhoneContainingOrNameContaining(any(String.class), any(String.class), any(String.class))).thenReturn(users);

        //Testing Document
        List<User> userByDocument = userService.findByDocumentOrPhoneOrNameContaining(user.getDocument());
        Assertions.assertThat(userByDocument).isNotEmpty();

        //Testing Phone
        List<User> userByPhone = userService.findByDocumentOrPhoneOrNameContaining(user.getPhone());
        Assertions.assertThat(userByPhone).isNotEmpty();

        //Testing Name
        List<User> userByName = userService.findByDocumentOrPhoneOrNameContaining(user.getName());
        Assertions.assertThat(userByName).isNotEmpty();
    }

    @Test
    void userFindAllSuccessTest() {

        //Inserting 2 users before testing
        User user = User.generateUserForTesting("Thiago", "1234567", "thiago@gmail.com",
                "thiago", "5512981126586", "123456789");

        User secondUser = User.generateUserForTesting("Luis", "222222", "luis@gmail.com",
                "luis", "5512982226586", "1897665");

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(secondUser);

        when(userRepository.findAll()).thenReturn(users);

        List<User> expectedUsers = userService.findAll();
        Assertions.assertThat(expectedUsers).isNotEmpty();
        Assertions.assertThat(expectedUsers.size()).isEqualTo(2);
    }
}