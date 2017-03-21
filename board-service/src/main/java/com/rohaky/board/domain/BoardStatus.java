package com.rohaky.board.domain;

import com.rohaky.board.exception.DuplicateBoardStatusException;

import javax.persistence.*;
import java.time.LocalDate;

@Embeddable
public class BoardStatus {

  @Transient
  public static final LocalDate NULL_DATE = LocalDate.of(9999, 1, 1);

  enum Type {
    OPEN, CLOSE
  }

  @Column(name = "BOARD_STATUS", length = 10)
  @Enumerated(EnumType.STRING)
  private Type type;

  @Column(name = "OPEN_DATE", columnDefinition = "DATE DEFAULT '9999-01-01'")
  private LocalDate openDate;

  @Column(name = "CLOSE_DATE", columnDefinition = "DATE DEFAULT '9999-01-01'")
  private LocalDate closeDate;

  // JPA only
  protected BoardStatus() {
    this(Type.OPEN);
  }

  BoardStatus(Type type) {
    this.type = type;
    this.openDate = (type == Type.OPEN) ? LocalDate.now() : NULL_DATE;
    this.closeDate = (type == Type.CLOSE) ? LocalDate.now() : NULL_DATE;
  }

  private BoardStatus(Type type, LocalDate openDate, LocalDate closeDate) {
    this.type = type;
    this.openDate = openDate;
    this.closeDate = closeDate;
  }

  BoardStatus open() {
    return changeStatus(Type.OPEN);
  }

  private BoardStatus changeStatus(Type type) {
    if (this.type == type) {
      throw new DuplicateBoardStatusException(type.toString());
    }

    switch (type) {
      case OPEN:
        return new BoardStatus(type, LocalDate.now(), NULL_DATE);
      case CLOSE:
        return new BoardStatus(type, NULL_DATE, LocalDate.now());
      default:
        throw new IllegalArgumentException(type.toString() + "is not permitted");
    }
  }

  BoardStatus close() {
    return changeStatus(Type.CLOSE);
  }

  boolean isOpen() {
    return type == Type.OPEN;
  }

  LocalDate getOpenDate() {
    return openDate;
  }

  LocalDate getCloseDate() {
    return closeDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BoardStatus that = (BoardStatus) o;

    if (type != that.type) return false;
    if (openDate != null ? !openDate.equals(that.openDate) : that.openDate != null) return false;
    return closeDate != null ? closeDate.equals(that.closeDate) : that.closeDate == null;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (openDate != null ? openDate.hashCode() : 0);
    result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "BoardStatus{" +
      "type=" + type +
      ", openDate=" + openDate +
      ", closeDate=" + closeDate +
      '}';
  }
}
