package com.colegio.prevost.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.colegio.prevost.dto.UserDTO;
import com.colegio.prevost.service.delegate.UserDeletage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDeletage userDelegate;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userDelegate.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String username) {
        UserDTO dto = userDelegate.getUserById(username);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        UserDTO created = userDelegate.createUser(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + created.getUsername()))
                .body(created);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String username,
            @RequestBody UserDTO user) {

        UserDTO updated = userDelegate.updateUser(username, user);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userDelegate.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
}
