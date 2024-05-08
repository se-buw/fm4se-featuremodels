package de.buw.fm4se.featuremodels;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.buw.fm4se.featuremodels.fm.CrossTreeConstraint;
import de.buw.fm4se.featuremodels.fm.Feature;
import de.buw.fm4se.featuremodels.fm.FeatureModel;
import de.buw.fm4se.featuremodels.fm.GroupKind;

public class ProductsPreservedTest {

  @Test
  void testProductPreservedSingleFeature() {
    FeatureModel fm1 = getValid11Fm();
    FeatureModel fm2 = getValid12Fm();
    assertEquals(true, FeatureModelAnalyzer.checkAllProductsPreserved(fm1, fm2), "Expect first FM set of products to be subset of second FM, but got false");
    
    fm2.getRoot().getChildren().get(0).setMandatory(true);
    assertEquals(false, FeatureModelAnalyzer.checkAllProductsPreserved(fm1, fm2), "Doesn't expect first FM set of products to be subset of second FM, but got true");
  }
  
  @Test
  void testMandatoryAndRequires() {
    FeatureModel fm1 = getValid21Fm();
    FeatureModel fm2 = getValid22Fm();
    assertEquals(true, FeatureModelAnalyzer.checkAllProductsPreserved(fm1, fm2), "Expect first FM set of products to be subset of second FM, but got false");
    assertEquals(false, FeatureModelAnalyzer.checkAllProductsPreserved(fm2, fm1), "Doesn't expect first FM set of products to be subset of second FM, but got true");
  }

  @Test
  void testCompletelyDifferentFeatureModel() {
    FeatureModel fm1 = getValid21Fm();
    FeatureModel fm2 = getValid23Fm();
    assertEquals(false, FeatureModelAnalyzer.checkAllProductsPreserved(fm1, fm2), "Sets of products are completely different, but got true");
    assertEquals(false, FeatureModelAnalyzer.checkAllProductsPreserved(fm2, fm1), "Sets of products are completely different, but got true");
  }


  /*
   * Returns a fm with single feature
   */
  public static FeatureModel getValid11Fm() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);

    return m;
  }

  /*
   * Variants of the getValid11Fm() with additional optional features
   * Returns a fm with single feature
   */
  public static FeatureModel getValid12Fm() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);

    car.addChild("motor", false);

    return m;
  }


  /*
   * Returns a fm with single feature
   */
  public static FeatureModel getValid21Fm() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);
    car.setChildGroupKind(GroupKind.OR);

    Feature motor = car.addChild("motor", false);
    Feature pedals = car.addChild("pedals", false);

    m.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, motor));
    m.addConstraint(new CrossTreeConstraint(car, CrossTreeConstraint.Kind.REQUIRES, pedals));

    return m;
  }

  /*
   * Variants of the getValid11Fm() with additional optional features
   * Returns a fm with single feature
   */
  public static FeatureModel getValid22Fm() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("car");
    m.setRoot(car);

    Feature motor = car.addChild("motor", false);
    car.addChild("pedals", false);

    motor.addChild("valve", false);

    
    return m;
  }
  
    /*
   * Variants of the getValid11Fm() with additional optional features
   * Returns a fm with single feature
   */
  public static FeatureModel getValid23Fm() {
    FeatureModel m = new FeatureModel();

    Feature car = new Feature("eshop");
    m.setRoot(car);

    car.addChild("search", false);

    
    return m;
  }
}
