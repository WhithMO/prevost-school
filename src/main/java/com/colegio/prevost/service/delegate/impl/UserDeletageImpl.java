package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.UserDTO;
import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.UserDeletage;
import com.colegio.prevost.util.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDeletageImpl implements UserDeletage {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserDTO getUserById(String username) {
        return mapper.toDto(userRepository.findByUsername(username));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(mapper::toDto).toList();
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        return mapper.toDto(userRepository.save(mapper.toEntity(user)));
    }

    @Override
    public UserDTO updateUser(String username, UserDTO user) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
           existingUser.setNames(user.getNames());
           existingUser.setSurNames(user.getSurNames());
           existingUser.setEmail(user.getEmail());
           existingUser.setRoles(user.getRoles());
           userRepository.save(existingUser);
           return user;
        }
        return null;
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            existingUser.setPassword(newPassword);
            userRepository.save(existingUser);
        }
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

}
