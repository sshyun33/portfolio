package com.rohaky.board.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.rohaky.board.common.JpaUtils.assertPersisted;

@Entity
@Table(name = "TBL_BOARD")
public class Board implements Serializable {

  @Transient
  private static final Object LOCK = new Object();

  @Id
  @GeneratedValue
  @Column(name = "BOARD_ID")
  private Long id;

  @NotEmpty
  @Column(name = "BOARD_TITLE", nullable = false, length = 100)
  private String title;

  @Column(name = "BOARD_DESCRIPTION", length = 300)
  private String description;

  @Embedded
  private BoardStatus status = new BoardStatus(BoardStatus.Type.OPEN);

  @Column(name = "CREATED_TIME", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP()")
  private LocalDateTime createdTime = LocalDateTime.now();

  @Column(name = "ARTICLE_NO_COUNTER", columnDefinition = "INTEGER(10) DEFAULT 0")
  private int articleNoCounter = 0;

  // JPA only
  protected Board() {
  }

  public Board(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void open() {
    this.status = status.open();
  }

  public void close() {
    this.status = status.close();
  }

  public int increaseArticleNoAndGet() {
    assertPersisted(id);

    synchronized (LOCK) {
      return articleNoCounter++;
    }
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public boolean isOpen() {
    return status.isOpen();
  }

  public LocalDate getOpenDate() {
    return status.getOpenDate();
  }

  public LocalDate getCloseDate() {
    return status.getCloseDate();
  }


  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public int getArticleNoCounter() {
    return articleNoCounter;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Board board = (Board) o;

    return id != null ? id.equals(board.id) : board.id == null;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Board{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", description='" + description + '\'' +
      ", status='" + status + '\'' +
      ", openDate='" + status.getOpenDate() + '\'' +
      ", closeDate='" + status.getCloseDate() + '\'' +
      ", createdTime='" + createdTime + '\'' +
      ", articleCounter='" + articleNoCounter + '\'' +
      '}';
  }
}
