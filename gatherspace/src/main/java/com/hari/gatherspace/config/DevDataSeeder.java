package com.hari.gatherspace.config;

import com.hari.gatherspace.features.avatar.Avatar;
import com.hari.gatherspace.features.avatar.AvatarService;
import com.hari.gatherspace.features.avatar.CreateAvatarRequest;
import com.hari.gatherspace.features.element.CreateElementRequest;
import com.hari.gatherspace.features.element.DefaultElement;
import com.hari.gatherspace.features.element.ElementService;
import com.hari.gatherspace.features.map.CreateMapRequest;
import com.hari.gatherspace.features.map.MapService;
import com.hari.gatherspace.features.space.Space;
import com.hari.gatherspace.features.space.SpaceService;
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

import java.util.List;
import java.util.Optional;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DevDataSeeder {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AvatarService avatarService;
    private final ElementService elementService;
    private final MapService mapService;
    private final SpaceService spaceService;

    @Bean
    public CommandLineRunner seedData(){
        return args -> {
            log.info("Data seed for the Dev environment is initialized");

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


            log.info("Creating assets {Avatar, Elements & Maps}");
            CreateAvatarRequest avatarAsh = CreateAvatarRequest.builder().name("ash").imageUrl("https://avatar.iran.liara.run/public/boy?username=Ash").build();
            CreateAvatarRequest avatarSugan = CreateAvatarRequest.builder().name("sugan").imageUrl("https://png.pngtree.com/png-clipart/20231019/original/pngtree-user-profile-avatar-png-image_13369988.png").build();
            CreateAvatarRequest avatarHari = CreateAvatarRequest.builder().name("hari").imageUrl("https://www.shareicon.net/data/512x512/2016/05/24/770137_man_512x512.png").build();

            avatarService.createAvatar(avatarAsh);
            avatarService.createAvatar(avatarSugan);
            avatarService.createAvatar(avatarHari);


            CreateElementRequest table = CreateElementRequest.builder().imageUrl("https://png.pngtree.com/png-vector/20190115/ourmid/pngtree-wooden-table-furniture-dining-table-cartoon-png-image_368150.jpg").height(1).width(4).staticValue(true).build();
            CreateElementRequest tableTwo = CreateElementRequest.builder().imageUrl("https://png.pngtree.com/png-vector/20190115/ourmid/pngtree-wooden-table-furniture-dining-table-cartoon-png-image_368150.jpg").height(1).width(4).staticValue(true).build();

            var element = elementService.createElement(table);
            elementService.createElement(tableTwo);
            DefaultElement defaultElement = DefaultElement.builder().elementId(element.getId()).x(element.getHeight()).y(element.getWidth()).build();


            CreateMapRequest map = CreateMapRequest.builder().defaultElements(List.of(defaultElement)).dimensions("30x30").thumbnail("https://ecdn.teacherspayteachers.com/thumbitem/Blank-Grid-Map-5891891-1599938845/original-5891891-1.jpg").name("COC").build();

            mapService.createMap(map);

            Space space = Space.builder().name("COC test playground").height(30).width(30).creatorId(userService.getUser("admin").get().getId()).build();

            spaceService.createSpace(space, mapService.getAllMaps().get(0));


        };

    }
}
