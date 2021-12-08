package com.karaca.jwt.business.abstracts;

import com.karaca.jwt.entities.Role;
import com.karaca.jwt.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);


    User getUser(String username);

    List<User> getUsers();
}
