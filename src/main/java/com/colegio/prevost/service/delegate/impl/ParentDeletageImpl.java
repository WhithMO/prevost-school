package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.ParentDTO;
import com.colegio.prevost.model.Parent;
import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.ParentRepository;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.ParentDeletage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentDeletageImpl implements ParentDeletage {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;

    @Override
    public Parent getParentById(Long id) {
        return parentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent createParent(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public ParentDTO updateParent(Long id, Parent parent, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        Parent existingParent = parentRepository.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setCode(user.getCode());
            existingUser.setNames(user.getNames());
            existingUser.setSurNames(user.getSurNames());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
        }

        if (existingParent != null) {
            existingParent.setMobileNumber(parent.getMobileNumber());
            parentRepository.save(existingParent);
        }
        return new ParentDTO().getParentDTO(parent, user);
    }

    @Override
    public void deleteParent(Long id) {
        parentRepository.deleteById(id);
    }

}
