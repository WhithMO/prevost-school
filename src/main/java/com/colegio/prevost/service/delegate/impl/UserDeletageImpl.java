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
    public UserDTO getUserById(Long id) {
        return mapper.toDto(userRepository.findById(id).orElse(null));
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
    public UserDTO updateUser(Long id, UserDTO user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
           existingUser.setCode(user.getCode());
           existingUser.setNames(user.getNames());
           existingUser.setSurNames(user.getSurNames());
           existingUser.setEmail(user.getEmail());
           existingUser.setPassword(user.getPassword());
           existingUser.setRoles(user.getRoles());
           userRepository.save(existingUser);
           return user;
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
