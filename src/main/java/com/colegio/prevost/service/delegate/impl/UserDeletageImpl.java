package com.colegio.prevost.service.delegate.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.colegio.prevost.dto.UserDTO;
import com.colegio.prevost.model.User;
import com.colegio.prevost.repository.UserRepository;
import com.colegio.prevost.service.delegate.UserDeletage;
import com.colegio.prevost.util.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDeletageImpl implements UserDeletage {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserDTO getUserById(String username) {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new ServiceException("Recurso no encontrado: User username=" + username);
            }
            return mapper.toDto(user);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al obtener User username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(mapper::toDto).toList();
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al listar Users", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        try {
            return mapper.toDto(userRepository.save(mapper.toEntity(user)));
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al crear User", dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public UserDTO updateUser(String username, UserDTO user) {
        try {
            User existingUser = userRepository.findByUsername(username);
            if (existingUser == null) {
                throw new ServiceException("Recurso no encontrado: User username=" + username);
            }
            existingUser.setNames(user.getNames());
            existingUser.setSurNames(user.getSurNames());
            existingUser.setEmail(user.getEmail());
            existingUser.setRoles(user.getRoles());
            userRepository.save(existingUser);
            return user;
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar User username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        try {
            User existingUser = userRepository.findByUsername(username);
            if (existingUser == null) {
                throw new ServiceException("Recurso no encontrado: User username=" + username);
            }
            existingUser.setPassword(newPassword);
            userRepository.save(existingUser);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al actualizar contraseña para User username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }

    }

    @Override
    public void deleteUser(String username) {
        try {
            if (userRepository.findByUsername(username) == null) {
                throw new ServiceException("Recurso no encontrado: User username=" + username);
            }
            userRepository.deleteByUsername(username);
        } catch (DataAccessException dae) {
            log.error("Error de acceso a datos al eliminar User username={}", username, dae);
            throw new ServiceException("Error interno al procesar la solicitud");
        }
    }

}
