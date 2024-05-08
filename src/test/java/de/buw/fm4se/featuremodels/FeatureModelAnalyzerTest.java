package de.buw.fm4se.featuremodels;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.buw.fm4se.featuremodels.fm.FeatureModel;

class FeatureModelAnalyzerTest {

  @Test
  void testCheckConsistentCarFM() {
    assertTrue(FeatureModelAnalyzer.checkConsistent(ExampleFmCreator.getSimpleFm()), "Expect consistent FM, but got inconsistent");
  }
  
  @Test
  void testCheckConsistentBadFM() {
    assertFalse(FeatureModelAnalyzer.checkConsistent(ExampleFmCreator.getBadFm()), "Expect inconsistent FM, but got consistent");
  }
  
  @Test
  void testCheckDeadFeaturesBadFM() {
    FeatureModel fm = ExampleFmCreator.getBadFm();
    List<String> deadFeatures = FeatureModelAnalyzer.deadFeatureNames(fm);
    
    assertTrue(deadFeatures.contains(fm.getRoot().getName()), "Expec root feature is dead, but got " + deadFeatures);
    assertTrue(deadFeatures.contains(fm.getRoot().getChildren().get(0).getName()), "Expect dead child, but got " + deadFeatures);
  }
  
  @Test
  void testCheckDeadFeaturesCarFM() {
    FeatureModel fm = ExampleFmCreator.getSimpleFm();
    List<String> deadFeatures = FeatureModelAnalyzer.deadFeatureNames(fm);
    
    assertFalse(deadFeatures.contains(fm.getRoot().getName()), "Root is not dead, but got " + deadFeatures);
  }
 

}
