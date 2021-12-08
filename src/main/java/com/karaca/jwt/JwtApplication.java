package com.karaca.jwt;

import com.karaca.jwt.business.abstracts.UserService;
import com.karaca.jwt.entities.Role;
import com.karaca.jwt.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "admin"));
            userService.saveRole(new Role(null, "manager"));
            userService.saveRole(new Role(null, "user"));
            userService.saveRole(new Role(null, "superadmin"));


            userService.saveUser(new User(null, "ismail", "iso", "12345", new ArrayList<>()));
            userService.saveUser(new User(null, "kutsal", "kutsi", "12345", new ArrayList<>()));
            userService.saveUser(new User(null, "mihriban", "miro", "12345", new ArrayList<>()));
            userService.saveUser(new User(null, "fatih", "fatfat", "12345", new ArrayList<>()));

            userService.addRoleToUser("iso", "admin");
            userService.addRoleToUser("fatfat", "manager");
            userService.addRoleToUser("fatfat", "user");
            userService.addRoleToUser("miro", "admin");
            userService.addRoleToUser("kutsi", "superadmin");
            userService.addRoleToUser("iso", "superadmin");
            userService.addRoleToUser("miro", "user");

        };
    }

}
