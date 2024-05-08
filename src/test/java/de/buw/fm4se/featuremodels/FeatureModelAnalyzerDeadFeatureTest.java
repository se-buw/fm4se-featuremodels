package de.buw.fm4se.featuremodels;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.buw.fm4se.featuremodels.fm.CrossTreeConstraint;
import de.buw.fm4se.featuremodels.fm.Feature;
import de.buw.fm4se.featuremodels.fm.FeatureModel;
import de.buw.fm4se.featuremodels.fm.GroupKind;

class FeatureModelAnalyzerDeadFeatureTest {

  /**
   * test a simple FM where all features are dead
   * 
   * based on 
   */
  @Test
  void testCheckDeadFeaturesBadFM() {
    FeatureModel fm = getBadFmExcludes();
    List<String> deadFeatures = FeatureModelAnalyzer.deadFeatureNames(fm);
    
    assertTrue(deadFeatures.contains(fm.getRoot().getName()), "Expect Root feature is dead, but got " + deadFeatures);
    assertTrue(deadFeatures.contains(fm.getRoot().getChildren().get(0).getName()), "Expect dead child, but got " + deadFeatures);
    assertEquals(2, deadFeatures.size(), "Expect 2 dead features, but got " + deadFeatures.size());
  }
  
  @Test
  void testCheckNoDeadFeatures1FFM() {
    FeatureModel fm = getValid1FFm();
    List<String> deadFeatures = FeatureModelAnalyzer.deadFeatureNames(fm);
    
    assertTrue(deadFeatures.isEmpty(), "Expect no dead features, but got " + deadFeatures.size());
  }

  
  @Test
  void testCheckNoDeadFeatures2FFM() {
    FeatureModel fm = getValid2FFm();
    List<String> deadFeatures = FeatureModelAnalyzer.deadFeatureNames(fm);
    
    assertTrue(deadFeatures.isEmpty(), "Expect no dead features, but got " + deadFeatures.size());
  }
  
  @Test
  void testCheckValidFeaturesFM1Dead() {
    FeatureModel fm = getValidFmRequires();
    List<String> deadFeatures = FeatureModelAnalyzer.deadFeatureNames(fm);
    
    assertTrue(deadFeatures.contains("pedals"), "Expect dead feature pedals, but got " + deadFeatures);
    assertEquals(1, deadFeatures.size(), "Expect 1 dead feature, but got " + deadFeatures.size());
  }

  @Test
  void testCheckBadFM1Requires() {
    FeatureModel fm = getBadFmRequires();
    List<String> deadFeatures = FeatureModelAnalyzer.deadFeatureNames(fm);
    
    assertTrue(deadFeatures.contains("pedals"), "Expect dead feature pedals, but got " + deadFeatures);
    assertTrue(deadFeatures.contains("car"), "Expect dead feature car, but got " + deadFeatures);
    assertTrue(deadFeatures.contains("motor"), "Expect dead feature motor, but got " + deadFeatures);
    assertEquals(3, deadFeatures.size(), "Expect 3 dead features, but got " + deadFeatures.size());
  }


  /**
   * constructs a FM where the root requires both XOR children
   * @return
   */
  public static FeatureModel getBadFmRequires() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);
    car.setChildGroupKind(GroupKind.XOR);

    Feature motor = car.addChild("motor", false);
    Feature pedals = car.addChild("pedals", false);

    m.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, motor));
    m.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, pedals));

    return m;
  }
  
  /**
   * constructs a FM where the mandatory child excludes the root
   * @return
   */
  public static FeatureModel getValidFmRequires() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);
    car.setChildGroupKind(GroupKind.XOR);

    Feature motor = car.addChild("motor", false);
    car.addChild("pedals", false);

    m.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, motor));

    return m;
  }

  
  /**
   * constructs a FM where the mandatory child excludes the root
   * @return
   */
  public static FeatureModel getBadFmExcludes() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);

    Feature motor = car.addChild("motor", true);

    m.addConstraint(new CrossTreeConstraint(motor, CrossTreeConstraint.Kind.EXCLUDES, car));

    return m;
  }
  
  public static FeatureModel getValid2FFm() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);

    car.addChild("motor", true);

    return m;
  }
  
  public static FeatureModel getValid1FFm() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);

    return m;
  }
}
