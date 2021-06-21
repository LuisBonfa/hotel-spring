package com.senior.challenge.user.utils;

import com.senior.challenge.user.entity.Role;
import com.senior.challenge.user.entity.User;
import com.senior.challenge.user.entity.UserRole;
import com.senior.challenge.user.repository.RoleRepository;
import com.senior.challenge.user.repository.UserRepository;
import com.senior.challenge.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@Profile("!test")
@Component
public class InitializationControl implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Verifies if the user root was already created
        //If not, the user is created.
        Optional<Role> role = roleRepository.findByName("admin");
        Optional<User> user = userRepository.findByName("root");

        if (user.isEmpty()) {
            User rootUser = new User();
            rootUser.setName("root");
            rootUser.setAlias("root");
            rootUser.setPassword(Security.hash("root@123456"));
            rootUser.setTries(0);
            rootUser.setEmail("luisgbonfaprof@gmail.com");
            rootUser.setPhone("12988914622");
            rootUser.setDocument("34656312851");
            rootUser.setStatus("enabled");
            rootUser.setCreationDate();
            userRepository.save(rootUser);

            UserRole userRole = new UserRole();
            userRole.setUser(rootUser);
            userRole.setRole(role.get());
            userRole.setCreationDate();
            userRoleRepository.save(userRole);

        } else
            System.out.println("Root Already Registered");
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}