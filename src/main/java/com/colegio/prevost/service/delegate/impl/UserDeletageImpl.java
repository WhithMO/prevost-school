package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.UserDeletage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDeletageImpl implements UserDeletage {

    private final UserRepository repository;

    @Override
    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = repository.findById(id).orElse(null);
        if (existingUser != null) {
           existingUser.setCode(user.getCode());
           existingUser.setNames(user.getNames());
           existingUser.setSurNames(user.getSurNames());
           existingUser.setEmail(user.getEmail());
           existingUser.setPassword(user.getPassword());
           existingUser.setRoles(user.getRoles());
           return repository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

}
