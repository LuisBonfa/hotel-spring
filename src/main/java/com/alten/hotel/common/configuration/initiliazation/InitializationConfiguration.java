package com.alten.hotel.common.configuration.initiliazation;


import com.alten.hotel.common.enums.CommonStatus;
import com.alten.hotel.common.utils.Security;
import com.alten.hotel.role.entity.RoleEntity;
import com.alten.hotel.role.repository.RoleRepository;
import com.alten.hotel.user.entity.UserEntity;
import com.alten.hotel.user.repository.UserRepository;
import com.alten.hotel.userrole.entity.UserRoleEntity;
import com.alten.hotel.userrole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.TimeZone;

@Profile("!test")
@Component
public class InitializationConfiguration implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public void run(ApplicationArguments args) {
        Optional<UserEntity> user = userRepository.findByName("root");

        if (user.isEmpty()) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setName("admin");
            adminRole.setStatus(CommonStatus.ACTIVE);
            roleRepository.save(adminRole);

            RoleEntity normalRole = new RoleEntity();
            normalRole.setName("user");
            normalRole.setStatus(CommonStatus.ACTIVE);
            roleRepository.save(normalRole);

            UserEntity rootUser = new UserEntity();
            rootUser.setName("root");
            rootUser.setUsername("root");
            rootUser.setAlias("root");
            rootUser.setPassword(Security.hash("root@123456"));
            rootUser.setTries(0);
            rootUser.setEmail("luisgbonfaprof@gmail.com");
            rootUser.setPhone("12996731116");
            rootUser.setDocument("34656312851");
            rootUser.setStatus(CommonStatus.ACTIVE);
            userRepository.save(rootUser);

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUser(rootUser);
            userRole.setRole(adminRole);
            userRole.setStatus(CommonStatus.ACTIVE);
            userRoleRepository.save(userRole);

        } else {
            System.out.println("Root Already Registered");
        }
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}