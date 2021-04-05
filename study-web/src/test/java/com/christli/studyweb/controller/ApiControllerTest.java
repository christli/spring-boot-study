package com.christli.studyweb.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void getUsers() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/user/users"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getUser() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/user/users/2"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void addUser() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/user/users")
                .contentType(MediaType.APPLICATION_JSON).content("{ \"userId\": 3,\"age\": 3,\"userName\": \"tom\"}"))
                .andReturn();
        Assertions.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    void editUser() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/api/user/users/3")
                .contentType(MediaType.APPLICATION_JSON).content("{ \"userId\": 3,\"userName\": \"tom\"}"))
                .andReturn();
        Assertions.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    void deleteUser() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/api/user/users/3")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }
}