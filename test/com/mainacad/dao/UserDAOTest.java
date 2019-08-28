package com.mainacad.dao;

import com.mainacad.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserDAOTest {

    private static List<User> users = new ArrayList<>();
    @BeforeAll
    static void setUp() {
        User user = new User("test_login", "test_pass", "test_name", "test_surname");
        users.add(user);
    }

    @AfterAll
    static void tearDown() {
        users.stream().forEach(user -> UserDAO.delete(user.getId()));
    }

    @Test
    void createAndFindAndDelete() {
        assertNull(users.get(0).getId());
        User userInDB = UserDAO.create(users.get(0));

        assertNotNull(userInDB);
        assertNotNull(userInDB.getId());

        User checkedUserInDB = UserDAO.findById(userInDB.getId());
        assertNotNull(checkedUserInDB);

        User checkedUserInDBByLogin = UserDAO.findByLogin(userInDB.getLogin());
        assertNotNull(checkedUserInDBByLogin);

        User checkedUserInDBByAll = UserDAO.findAll();
        assertNotNull(checkedUserInDBByAll);

        UserDAO.delete(checkedUserInDB.getId());
        User deletedUser = UserDAO.findById(userInDB.getId());
        assertNull(deletedUser);
    }
}
