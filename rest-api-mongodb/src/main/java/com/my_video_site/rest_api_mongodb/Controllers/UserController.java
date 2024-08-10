package com.my_video_site.rest_api_mongodb.Controllers;


import com.my_video_site.rest_api_mongodb.Config.JwtUtil;
import com.my_video_site.rest_api_mongodb.Models.Movie;
import com.my_video_site.rest_api_mongodb.Models.User;
import com.my_video_site.rest_api_mongodb.Services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static javax.crypto.Cipher.SECRET_KEY;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/register", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (user.getFirstName() == null ||user.getLastName() == null || user.getPassword() == null || user.getEmail() == null) {
            return new ResponseEntity<>("Missing data. Email and password required", HttpStatus.BAD_REQUEST);
        }

        if (!isValidEmail(user.getEmail())) {
            return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
        }

        User newUser = userService.registerUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> credentials) {
//        String email = credentials.get("email");
//        String password = credentials.get("password");
//
//        if (email == null || password == null) {
//            return new ResponseEntity<>("Email and password required", HttpStatus.BAD_REQUEST);
//        }
//
//        User authUser = userService.authenticateUser(email, password);
//
//        if (authUser != null) {
//            return new ResponseEntity<>(authUser, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
//        }
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");

            if (email == null || password == null) {
                return new ResponseEntity<>("Email and password required", HttpStatus.BAD_REQUEST);
            }

            User authUser = userService.authenticateUser(email, password);

            if (authUser != null) {
                String token = jwtUtil.generateToken(authUser.getFirstName());
                Map<String, Object> response = new HashMap<>();
                response.put("user", authUser);
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace to the console
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Data
    public static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
    }
}

