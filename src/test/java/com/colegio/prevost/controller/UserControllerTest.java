package com.colegio.prevost.controller;

import com.colegio.prevost.dto.UserDTO;
import com.colegio.prevost.service.delegate.UserDeletage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDeletage userDelegate;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setNames("Test");
        userDTO.setSurNames("User");
    }

    @Test
    void getUserById_whenFound() throws Exception {
        given(userDelegate.getUserById("testuser")).willReturn(userDTO);

        mockMvc.perform(get("/api/users/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names", is("Test")));
    }

    @Test
    void createUser() throws Exception {
        given(userDelegate.createUser(any(UserDTO.class))).willReturn(userDTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePassword() throws Exception {
        doNothing().when(userDelegate).updatePassword("testuser", "newPass123");
        Map<String, String> payload = Map.of("newPassword", "newPass123");

        mockMvc.perform(put("/api/users/testuser/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updatePassword_badRequest() throws Exception {
        Map<String, String> payload = Map.of("newPassword", "");

        mockMvc.perform(put("/api/users/testuser/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }
}
