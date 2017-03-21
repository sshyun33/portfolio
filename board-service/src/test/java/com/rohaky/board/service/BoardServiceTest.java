package com.rohaky.board.service;

import com.rohaky.board.domain.Board;
import com.rohaky.board.repository.BoardRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {

  @Mock
  private BoardRepository boardRepository;

  @Mock
  private Board output;

  private Board input;

  private BoardService boardService;

  @Before
  public void setUp() throws Exception {
    boardService = new BoardService(boardRepository);

    given(output.getId()).willReturn(123L);
    given(boardRepository.save(any(Board.class))).willReturn(output);
  }

  @Test
  public void testCreateBoard() throws Exception {
    Board created = boardService.create(input);

    assertThat(created).isEqualTo(output);
    verify(boardRepository, times(1)).save(input);
  }

  @Test
  public void whenInvalidRequestThenValidException() throws Exception {
  }
}