package de.buw.fm4se.featuremodels;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.buw.fm4se.featuremodels.fm.CrossTreeConstraint;
import de.buw.fm4se.featuremodels.fm.Feature;
import de.buw.fm4se.featuremodels.fm.FeatureModel;
import de.buw.fm4se.featuremodels.fm.GroupKind;

class FeatureModelAnalyzerMandatoryFeatureTest {

  /**
   * test a simple FM where all features are dead
   * 
   * based on
   */
  @Test
  void testCheckDeadFeaturesBadFM() {
    FeatureModel fm = getBadFmExcludes();
    List<String> fs = FeatureModelAnalyzer.mandatoryFeatureNames(fm);

    assertTrue(fs.isEmpty(), "No mandatory features expected, but got " + fs.size());
  }

  @Test
  void testCheckNoDeadFeatures1FFM() {
    FeatureModel fm = getValid1FFm();
    List<String> fs = FeatureModelAnalyzer.mandatoryFeatureNames(fm);

    assertTrue(fs.contains("car"), "Expect mandatory feature car, but got " + fs);
    assertEquals(1, fs.size(), "Expect 1 mandatory feature, but got " + fs.size());
  }

  @Test
  void testCheckNoDeadFeatures2FFM() {
    FeatureModel fm = getValid2FFm();
    List<String> fs = FeatureModelAnalyzer.mandatoryFeatureNames(fm);

    assertTrue(fs.contains("car"), "Expect mandatory feature car, but got " + fs);
    assertEquals(1, fs.size(), "Expect 1 mandatory feature, but got " + fs.size());
    ;
  }

  @Test
  void testCheckValidFeaturesFM1Dead() {
    FeatureModel fm = getValidFmRequires();
    List<String> fs = FeatureModelAnalyzer.mandatoryFeatureNames(fm);

    assertTrue(fs.contains("car"), "Expect mandatory feature car, but got " + fs);
    assertTrue(fs.contains("motor"), "Expect mandatory feature motor, but got " + fs);
    assertEquals(2, fs.size(), "Expect 2 mandatory features, but got " + fs.size());
  }

  @Test
  void testCheckBadFM1Requires() {
    FeatureModel fm = getBadFmRequires();
    List<String> fs = FeatureModelAnalyzer.mandatoryFeatureNames(fm);

    assertTrue(fs.isEmpty(), "No mandatory features expected, but got " + fs.size());
  }

  /**
   * constructs a FM where the mandatory child excludes the root
   * 
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
   * 
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
   * 
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

    car.addChild("motor", false);

    return m;
  }

  public static FeatureModel getValid1FFm() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);

    return m;
  }
}
