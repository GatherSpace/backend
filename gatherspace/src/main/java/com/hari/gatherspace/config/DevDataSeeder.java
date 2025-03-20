package com.hari.gatherspace.config;

import com.hari.gatherspace.features.user.Role;
import com.hari.gatherspace.features.user.User;
import com.hari.gatherspace.features.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DevDataSeeder {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedData(){
        return args -> {
            log.info("Seeding data...");

            String password = passwordEncoder.encode("1234");
            Optional<User> adminUserOptional = userService.getUser("admin");

            if(adminUserOptional.isEmpty()){
                User user = User.builder().username("admin").password(password).role(Role.Admin).build();
                userService.saveUser(user);
                log.info("Created admin user");
            }else {
                log.info("Admin user already exists");
            }

            for (int i = 1; i < 4; i++) {
                String testUserName = "test" + i;
                Optional<User> testUserOptional = userService.getUser(testUserName);
                if (testUserOptional.isEmpty()) {
                    // Create the test user
                    User testUser = new User();
                    testUser.setUsername(testUserName);
                    testUser.setPassword(password);
                    testUser.setRole(Role.User);

                    // Save the test user
                    userService.saveUser(testUser);
                    log.info("Test user created: username={}, password=1234", testUserName);
                }else {
                    log.info("Test user {} already exists.",testUserName);
                }            }
            log.info("Created users");
        };

    }
}
