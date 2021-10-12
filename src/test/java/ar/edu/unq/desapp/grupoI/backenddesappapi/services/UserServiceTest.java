package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import ar.edu.unq.desapp.grupoI.backenddesappapi.builders.UserBuilder;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.UserDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private UserDTO userDTO;

    @Before
    public void setUp() {
        User user = UserBuilder.anUser().build();

        userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setAddress(user.getAddress());
        userDTO.setPassword(user.getPassword());
        userDTO.setCVUMercadoPago(user.getCVUMercadoPago());
        userDTO.setWalletAddress(user.getWalletAddress());

        Mockito.when(userRepository.findByUsername("username")).thenReturn(user);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

    }

    @Test
    public void loadUserByUsername() {

        User user = UserBuilder.anUser().build();

        UserDetails userTest = userService.loadUserByUsername("username");

        assertThat(userTest.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void saveUserTest() {

        User user = UserBuilder.anUser().build();

        User userTest = userService.save(userDTO);

        assertThat(userTest.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userTest.getLastName()).isEqualTo(user.getLastName());
        assertThat(userTest.getEmail()).isEqualTo(user.getEmail());
        assertThat(userTest.getUsername()).isEqualTo(user.getUsername());
        assertThat(userTest.getAddress()).isEqualTo(user.getAddress());
        assertThat(userTest.getPassword()).isEqualTo(user.getPassword());
        assertThat(userTest.getCVUMercadoPago()).isEqualTo(user.getCVUMercadoPago());
        assertThat(userTest.getWalletAddress()).isEqualTo(user.getWalletAddress());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenLoadUserByUsername_ItShouldThrowAnExceptionIfTheUsernameDoesNotExists() {

        userService.loadUserByUsername("inexistentUsername");
    }
}