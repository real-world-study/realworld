package com.tistory.povia.realworld.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserController userController;

  private JoinRequest req;

  @BeforeEach
  void setup() {
    req = new JoinRequest("povia", "povia@test.com", "543212", null, null);
  }

  @Test
  @DisplayName("성공 테스트")
  void userJoinUserReturnTest() throws Exception {
    ResultActions resultActions = mockMvc.
      perform(
        MockMvcRequestBuilders
          .post("/api/users")
          .content(objectMapper.writeValueAsString(req))
          .contentType(MediaType.APPLICATION_JSON))
      .andDo(print());

    resultActions
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.user.username").value(req.username()))
      .andExpect(jsonPath("$.user.email").value(req.address()))
      .andExpect(jsonPath("$.user.bio").isEmpty())
      .andExpect(jsonPath("$.user.image").isEmpty())
      .andReturn()
    ;
  }

  @Test
  @DisplayName("중복 실패 테스트")
  void userJoinErrorReturnTest() throws Exception {

    userController.join(req);

    mockMvc.
      perform(
        MockMvcRequestBuilders
          .post("/api/users")
          .content(objectMapper.writeValueAsString(req))
          .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is4xxClientError())
      .andDo(print());
  }
}