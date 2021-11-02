package ar.edu.unq.desapp.grupoI.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoI.backenddesappapi.auth.JwtResponse;
import ar.edu.unq.desapp.grupoI.backenddesappapi.auth.JwtTokenUtil;
import ar.edu.unq.desapp.grupoI.backenddesappapi.auth.JwtRequest;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoI.backenddesappapi.model.UserDTO;
import ar.edu.unq.desapp.grupoI.backenddesappapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        User user = userDetailsService.getByUserName(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, user.getId()));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user, Locale locale) throws Exception {

        Map<String, Object> response = new HashMap<>();

        String message = messageSource.getMessage("ar.edu.unq.desapp.grupoI.backenddesappapi.validation.register", null, locale);

        try {
            return ResponseEntity.ok(userDetailsService.save(user));
        }
        catch (ConstraintViolationException e){

            response.put("message", message);
            response.put("error", Objects.requireNonNull(e.getConstraintViolations().toString()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}