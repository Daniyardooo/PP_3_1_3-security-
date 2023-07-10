package ru.kata.spring.boot_security.demo.appInit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private UserDao userDao;

    private RoleDao roleDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @PostConstruct
    public void postConstruct() {
        Role userRole = new Role(1L, "ROLE_USER");
        Role adminRole = new Role(2L, "ROLE_ADMIN");
        roleDao.save(userRole);
        roleDao.save(adminRole);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);
        User user = new User("Daniyardo", "$2a$12$dzR9OqICVGrCv.rGT6bPE.5B3pX0V16UGptogtkvKeAZd3T8j0nfq",
                "danhas@mail.ru", roles);
        userDao.save(user);
    }
}
