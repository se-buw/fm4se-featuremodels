package de.buw.fm4se.featuremodels.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class LimbooleExecutorTest {

  @Test
  void testLimbooleWorking() {
    try {
      LimbooleExecutor.runLimboole("a | !a", false);
    } catch (IOException | InterruptedException e) {
      fail(e);
    }
  }

}
