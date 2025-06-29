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

    @Override
    public ParentDTO getParentById(Long id) {
        Parent parent = parentRepository.findById(id).orElse(null);
        User user = userRepository.findById(id).orElse(null);
        if (parent != null && user != null) {
            return new ParentDTO().getParentDTOFromEntity(parent, user);
        }
        return null;
    }

    @Override
    public List<ParentDTO> getAllParents() {
        return parentRepository.findAll().stream()
                .map(parent -> getParentById(parent.getUserId()))
                .toList();
    }

    @Override
    public ParentDTO createParent(ParentDTO parent) {
        User user = userRepository.save(new User().getUserFromDto(parent));
        Parent savedParent = parentRepository.save(new Parent(user, parent.getMobileNumber()));
        return mapper.toDto(savedParent);
    }

    @Override
    public ParentDTO updateParent(Long id, ParentDTO parent) {
        User existingUser = userRepository.findById(id).orElse(null);
        Parent existingParent = parentRepository.findById(id).orElse(null);

        if (existingUser != null && existingParent != null) {
            existingUser.setCode(parent.getCode());
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
    public void deleteParent(Long id) {
        parentRepository.deleteById(id);
    }

}
