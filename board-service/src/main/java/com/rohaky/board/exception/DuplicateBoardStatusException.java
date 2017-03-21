package com.rohaky.board.exception;

public class DuplicateBoardStatusException extends RuntimeException {

  public DuplicateBoardStatusException(String boardStatus) {
    super(String.format("This board is already %s.", boardStatus));
  }
}
