package com.mainacad.service;

import com.mainacad.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        User user = new User("test_login", "test_pass", "test_name", "test_surname");
        users.add(user);
    }

    @AfterEach
    void tearDown() {
        users.stream().forEach(user -> UserService.delete(user.getId()));
    }

    @Test
    void create() {
        assertNull(users.get(0).getId());
        User userInDB = UserService.create(users.get(0));

        assertNotNull(userInDB);
        assertNotNull(userInDB.getId());

        User checkedUserInDB = UserService.findById(userInDB.getId());
        assertNotNull(checkedUserInDB);

        User checkedUserInDBByLogin = UserService.findByLogin(userInDB.getLogin());
        assertNotNull(checkedUserInDBByLogin);

        User checkedUserInDBByAll = UserService.findAll();
        assertNotNull(checkedUserInDBByAll);

        User checkedAuth = UserService.findUserByLoginAndPassword(userInDB.getLogin(), userInDB.getPassword());
        assertNull(checkedAuth);

        User checkedUpdate = UserService.update(userInDB);
        assertEquals(userInDB, checkedUpdate);

        UserService.delete(checkedUserInDB.getId());
        User deletedUser = UserService.findById(userInDB.getId());

        assertNull(deletedUser);
    }
}