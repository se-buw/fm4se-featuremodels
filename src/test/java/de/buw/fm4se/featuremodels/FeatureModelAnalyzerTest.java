package de.buw.fm4se.featuremodels;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FeatureModelAnalyzerTest {

  @Test
  void testCheckConsistentCarFM() {
    assertTrue(FeatureModelAnalyzer.checkConsistent(ExampleFmCreator.getSimpleFm()));
  }
  
  @Test
  void testCheckConsistentBadFM() {
    assertFalse(FeatureModelAnalyzer.checkConsistent(ExampleFmCreator.getBadFm()));
  }

}
