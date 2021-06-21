package com.senior.challenge.user.service;

import com.senior.challenge.user.entity.User;
import com.senior.challenge.user.repository.UserRepository;
import com.senior.challenge.user.security.JwtTokenUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AuthenticationServiceTest.class})
@ActiveProfiles("test")
@Profile("test")
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    private void setUp() {
        authenticationService = new AuthenticationService(jwtTokenUtil, userRepository);
    }

    @Test
    void loadUserByUsername() {
        User user = User.generateUserForTesting("Thiago", "34656312851", "luisbonfire@gmail.com",
                "thiago", "55988914622", "123456789");

        when(userRepository.findByName(user.getName())).thenReturn(Optional.of(user));
        UserDetails userDetails = authenticationService.loadUserByUsername(user.getName());

        verify(userRepository, times(1)).findByName(user.getName());
        Assertions.assertThat(userDetails).isNotNull();
        Assertions.assertThat(userDetails.getUsername()).isEqualTo(user.getName());
    }

    @Test
    void verifyUserCredentials() {
        User user = User.generateUserForTesting("Thiago", "34656312851", "luisbonfire@gmail.com",
                "thiago", "55988914622", "123456789");

        when(userRepository.findByName(user.getName())).thenReturn(Optional.of(user));
        Authentication authentication = authenticationService.verifyUserCredentials(user.getName(), "123456789");

        verify(userRepository, times(1)).findByName(user.getName());
        Assertions.assertThat(authentication).isNotNull();
        Assertions.assertThat(authentication.getPrincipal()).isEqualTo(user.getName());
        Assertions.assertThat(authentication.getCredentials()).isEqualTo("123456789");
    }
}