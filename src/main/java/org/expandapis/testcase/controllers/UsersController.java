package org.expandapis.testcase.controllers;

import jakarta.validation.Valid;
import org.expandapis.testcase.models.User;
import org.expandapis.testcase.security.UserDetailsImpl;
import org.expandapis.testcase.security.jwt.JwtUtils;
import org.expandapis.testcase.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    @Autowired
    public UsersController(UsersService usersService, AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User userToAdd) {
        if (usersService.findByUserName(userToAdd.getUsername()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body("This username is already taken!");
        }
        User user = new User();
        user.setUsername(userToAdd.getUsername());
        user.setPassword(encoder.encode(userToAdd.getPassword()));
        usersService.save(user);
        return ResponseEntity.ok("User added successfully");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody User userToAuth) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userToAuth.getUsername(), userToAuth.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(jwt);

    }

}