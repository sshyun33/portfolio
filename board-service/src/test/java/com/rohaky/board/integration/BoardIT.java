package com.rohaky.board.integration;

import com.rohaky.board.domain.Board;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestEntityManager
@AutoConfigureMockMvc
public class BoardIT {

  public static final String BOARD_BASE_URL = "http://localhost/v1/boards";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private TestEntityManager em;

  private String inputJson;

  @Before
  public void setUp() throws Exception {
    inputJson = "{\"title\": \"title\", \"description\": \"description\"}";
  }

  @Test
  @Transactional
  public void testWriteBoard() throws Exception {

    String location =
      mvc.perform(post("/v1/boards")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(inputJson))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getHeader("Location");

    Long boardId = assertLocationAndGetId(location);
    assertPersist(boardId);
  }

  private Long assertLocationAndGetId(String location) {
    assertThat(location).matches(BOARD_BASE_URL + "/\\d+");

    String createdId =
      location.substring(location.lastIndexOf("/") + 1, location.length());

    return Long.parseLong(createdId);
  }

  private void assertPersist(Long boardId) {
    Board board = em.find(Board.class, boardId);
    assertThat(board).isNotNull();
  }
}
