package com.example.demo.Testing;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);

    }
    
    @Test
    public void creatUser() throws Exception{
        when(bCryptPasswordEncoder.encode("password")).thenReturn("thisishashed");

        // Creates the CreateUserRequest Object, which is what is passed to the userController
        // createUser method
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("password");
        r.setConfirmPassword("password");

        // Pass the above CreateUserRequest Object to the userController
        final ResponseEntity<User> response = userController.createUser(r);

        // Make assertions on the response entity
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User u = response.getBody();
        assertNotNull(u);
        assertEquals("test",u.getUsername());
        assertEquals("thisishashed", u.getPassword());
    }
}
