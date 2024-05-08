package de.buw.fm4se.featuremodels;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import de.buw.fm4se.featuremodels.fm.CrossTreeConstraint;
import de.buw.fm4se.featuremodels.fm.Feature;
import de.buw.fm4se.featuremodels.fm.FeatureModel;
import de.buw.fm4se.featuremodels.fm.GroupKind;

class FeatureModelAnalyzerTranslationTest {

  /**
   * test a simple FM where all features are dead
   * 
   * based on
   * 
   * @throws InterruptedException
   * @throws IOException
   */
  @Test
  void testSingleFeature() {
    FeatureModel fm = getSingleFeatureFM();
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Expect consistent FM, but got inconsistent");
  }

  @Test
  void testMandatoryFeature() {
    FeatureModel fm = new FeatureModel();

    Feature car = new Feature("car");
    fm.setRoot(car);
    Feature motor = car.addChild("motor", true);

    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Expect consistent FM, but got inconsistent");

    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.EXCLUDES, motor));
    assertFalse(FeatureModelAnalyzer.checkConsistent(fm), "Mandatory feature was excluded, expecting inconsistent");

    motor.setMandatory(false);
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Optional feature was excluded, expecting consistent");
  }

  @Test
  void testORFeature() {
    FeatureModel fm = new FeatureModel();

    Feature car = new Feature("car");
    fm.setRoot(car);
    car.setChildGroupKind(GroupKind.OR);
    Feature c1 = car.addChild("c1", false);
    Feature c2 = car.addChild("c2", false);
    Feature c3 = car.addChild("c3", false);

    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Expect consistent FM, but got inconsistent");

    // try excluding features until its inconsistent
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.EXCLUDES, c1));
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Excluded one child, expecting consistent");
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.EXCLUDES, c2));
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Excluded one child, expecting consistent");
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.EXCLUDES, c3));
    assertFalse(FeatureModelAnalyzer.checkConsistent(fm), "Excluded last child, expecting inconsistent");

    fm.getConstraints().clear();

    // try requiring features until its inconsistent
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, c1));
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Requires one child, expecting consistent");
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, c2));
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Requires one child, expecting consistent");
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, c3));
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Requires one child, expecting consistent");
  }

  @Test
  void testParentChild() {
    FeatureModel fm = new FeatureModel();

    Feature car = new Feature("car");
    fm.setRoot(car);
    Feature motor = car.addChild("motor", false);
    Feature valve = motor.addChild("valce", false);

    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Expect consistent FM, but got inconsistent");

    fm.addConstraint(new CrossTreeConstraint(valve, CrossTreeConstraint.Kind.EXCLUDES, motor));
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, valve));

    assertFalse(FeatureModelAnalyzer.checkConsistent(fm), "Included child when parent was not there. Expecting inconsistent");
  }

  @Test
  void testXORFeature() {
    FeatureModel fm = new FeatureModel();

    Feature car = new Feature("car");
    fm.setRoot(car);
    car.setChildGroupKind(GroupKind.XOR);
    Feature c1 = car.addChild("c1", false);
    Feature c2 = car.addChild("c2", false);
    Feature c3 = car.addChild("c3", false);

    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Expect consistent FM, but got inconsistent");

    // try excluding features until its inconsistent
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.EXCLUDES, c1));
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Excluded one child, expecting consistent");
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.EXCLUDES, c2));
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Excluded one child, expecting consistent");
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.EXCLUDES, c3));
    assertFalse(FeatureModelAnalyzer.checkConsistent(fm), "Excluded last child, expecting inconsistent");

    fm.getConstraints().clear();

    // try requiring features until its inconsistent
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, c1));
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Requires one child, expecting consistent");
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, c2));
    assertFalse(FeatureModelAnalyzer.checkConsistent(fm), "Two children included in an XOR. Expecting inconsistent");
    fm.getConstraints().remove(0);
    assertTrue(FeatureModelAnalyzer.checkConsistent(fm), "Requires one child, expecting consistent");
    fm.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, c3));
    assertFalse(FeatureModelAnalyzer.checkConsistent(fm), "Two children included in an XOR. Expecting inconsistent");
  }

  public static FeatureModel getSingleFeatureFM() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);

    return m;
  }

  public static FeatureModel getMandatory() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);
    car.addChild("motor", true);

    return m;
  }

}
