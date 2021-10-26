package ar.edu.unq.desapp.grupoI.backenddesappapi.services;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.UserDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public User save(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        newUser.setCVUMercadoPago(user.getCVUMercadoPago());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setWalletAddress(user.getWalletAddress());
        return userRepository.save(newUser);
    }

    public ArrayList<UserDTO> getUsers() {

        List<User> users = userRepository.findAll();

        ArrayList<UserDTO> response = new ArrayList<>();

        users.forEach(user -> response.add(this.mapUser(user)));

        return response;
    }

    private UserDTO mapUser(User user){

        UserDTO newUser = new UserDTO();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setAddress(user.getAddress());
        newUser.setCVUMercadoPago(user.getCVUMercadoPago());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setWalletAddress(user.getWalletAddress());

        return newUser;
    }
}
