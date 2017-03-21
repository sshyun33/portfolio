package com.rohaky.board.controller;

import com.rohaky.board.domain.Board;
import com.rohaky.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/boards")
public class BoardApiController {

  private BoardService boardService;

  @Autowired
  public BoardApiController(BoardService boardService) {
    this.boardService = boardService;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Board input) {

    Board created = boardService.create(
      new Board(input.getTitle(), input.getDescription()));

    URI locationUri = ServletUriComponentsBuilder
      .fromCurrentRequest().path("/{id}")
      .buildAndExpand(created.getId()).toUri();

    return ResponseEntity.created(locationUri).build();
  }
}
