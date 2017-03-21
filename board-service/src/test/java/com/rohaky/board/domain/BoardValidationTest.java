package com.rohaky.board.domain;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardValidationTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validator;

  @BeforeClass
  public static void createValidator() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @AfterClass
  public static void close() {
    validatorFactory.close();
  }

  @Test
  public void testValidTitle() throws Exception {
    Board board = new Board("", "description");

    Set<ConstraintViolation<Board>> violations = validator.validate(board);
    assertThat(violations).hasSize(1);

    ConstraintViolation<Board> firstViolation = violations.iterator().next();
    assertThat(firstViolation.getMessage()).isEqualTo("may not be empty");
    assertThat(firstViolation.getPropertyPath().toString())
      .isEqualTo("title");
    assertThat(firstViolation.getInvalidValue()).isEqualTo("");
  }
}