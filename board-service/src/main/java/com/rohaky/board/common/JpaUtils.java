package com.rohaky.board.common;

import com.rohaky.board.exception.NotYetPersistInJpaException;

public class JpaUtils {

  private JpaUtils() {
  }

  public static void assertPersisted(Object id) {
    if (id == null) {
      throw new NotYetPersistInJpaException();
    }
  }
}
