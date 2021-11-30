package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import ar.edu.unq.desapp.grupoI.backenddesappapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.util.AssertionErrors.assertTrue;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserTests {
    @Autowired
    private UserService userService;

    public UserTests(){

    }

    @Test
    public void getAllUsers() {
        ArrayList<UserDTO> users = this.userService.getUsers();

        assertTrue("Users not found", users.size() > 0);
    }

    @Test
    public void getByUsername() {
        User user = this.userService.getByUserName("backenddesappapi");

        assertTrue("User not found", user.getUsername().equals("backenddesappapi"));
    }
}