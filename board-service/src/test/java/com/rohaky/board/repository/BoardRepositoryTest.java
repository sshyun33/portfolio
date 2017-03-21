package com.rohaky.board.repository;

import com.rohaky.board.domain.Board;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Converter;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
  type = FilterType.ANNOTATION, classes = {Converter.class}))
public class BoardRepositoryTest {

  @Autowired
  private TestEntityManager em;

  @Autowired
  private BoardRepository boardRepository;

  private Board board;

  @Before
  public void setUp() throws Exception {
    board = new Board("title", "description");
  }

  @Test
  public void testSaveBoard() throws Exception {
    Board saved = boardRepository.save(board);

    Board found = em.find(Board.class, saved.getId());

    assertThat(saved).isEqualTo(found);
  }
}