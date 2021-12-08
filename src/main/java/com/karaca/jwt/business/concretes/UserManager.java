package com.karaca.jwt.business.concretes;

import com.karaca.jwt.business.abstracts.UserService;
import com.karaca.jwt.entities.Role;
import com.karaca.jwt.entities.User;
import com.karaca.jwt.repo.RoleDao;
import com.karaca.jwt.repo.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserManager implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);
        if (user == null) {
            log.error("user not found in the database");
            throw new UsernameNotFoundException("user not found in the database");

        } else
            log.info("user found in the database: {}", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("saving {} to datebase", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving {} to datebase", role.getName());
        return roleDao.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("saving {} to {}", roleName, username);
        User user = userDao.findByUsername(username);
        Role role = roleDao.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("listed user {} ", username);
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("listed all users");
        return userDao.findAll();
    }


}
