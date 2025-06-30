package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.ParentDTO;
import com.colegio.prevost.model.Parent;
import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.ParentRepository;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.ParentDeletage;
import com.colegio.prevost.util.mapper.ParentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentDeletageImpl implements ParentDeletage {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final ParentMapper mapper;

    public ParentDTO getParentByUsername(String username) {
        Parent parent = parentRepository.findByUserUsername(username);
        if (parent != null) {
            User user = userRepository.findById(parent.getUserId()).orElse(null);
            return new ParentDTO().getParentDTOFromEntity(parent, user);
        }
        return null;
    }

    @Override
    public List<ParentDTO> getAllParents() {
        return parentRepository.findAll().stream()
                .map(parent -> getParentByUsername(parent.getUser().getUsername()))
                .toList();
    }

    @Override
    public ParentDTO createParent(ParentDTO parent) {
        User user = userRepository.save(new User().getUserFromDto(parent));
        Parent savedParent = parentRepository.save(new Parent(user, parent.getMobileNumber()));
        return mapper.toDto(savedParent);
    }

    @Override
    public ParentDTO updateParent(String username, ParentDTO parent) {
        Parent existingParent = parentRepository.findByUserUsername(username);
        if (existingParent != null) {
            User existingUser = userRepository.findById(existingParent.getUserId()).orElse(null);
            existingUser.setNames(parent.getNames());
            existingUser.setSurNames(parent.getSurNames());
            existingUser.setEmail(parent.getEmail());
            userRepository.save(existingUser);

            existingParent.setMobileNumber(parent.getMobileNumber());
            parentRepository.save(existingParent);
            return parent;
        }

       return null;
    }

    @Override
    public void deleteParent(String username) {
        parentRepository.deleteByUserUsername(username);
    }

}
