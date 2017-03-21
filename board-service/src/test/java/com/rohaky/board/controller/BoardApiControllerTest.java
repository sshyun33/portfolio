package com.rohaky.board.controller;

import com.rohaky.board.domain.Board;
import com.rohaky.board.service.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BoardApiController.class)
public class BoardApiControllerTest {

  private static final String BOARD_BASE_URL = "http://localhost/v1/boards";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BoardService boardService;

  @MockBean
  private Board output;

  private String inputJson;

  @Before
  public void setUp() throws Exception {
    inputJson = "{\"title\": \"title\", \"description\": \"description\"}";

    given(this.boardService.create(any(Board.class)))
      .willReturn(output);

    given(this.output.getId())
      .willReturn(123L);

  }

  @Test
  public void givenValidBoardWhenCreateBoardThenCreated() throws Exception {
    this.mockMvc.perform(post("/v1/boards")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(inputJson))
      .andExpect(status().isCreated())
      .andExpect(header().string("Location", BOARD_BASE_URL + "/" + output.getId()));
  }
}
