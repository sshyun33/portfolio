package com.rohaky.board.service;

import com.rohaky.board.domain.Board;
import com.rohaky.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
@Service
@Transactional
public class BoardService {

  private BoardRepository boardRepository;

  @Autowired
  public BoardService(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public Board create(@Valid Board board) {
    return boardRepository.save(board);
  }
}
