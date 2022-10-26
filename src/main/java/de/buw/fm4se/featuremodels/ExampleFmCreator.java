package de.buw.fm4se.featuremodels;

import de.buw.fm4se.featuremodels.fm.CrossTreeConstraint;
import de.buw.fm4se.featuremodels.fm.Feature;
import de.buw.fm4se.featuremodels.fm.FeatureModel;
import de.buw.fm4se.featuremodels.fm.GroupKind;

public class ExampleFmCreator {

  public static FeatureModel getEshopFm () {
    FeatureModel m = new FeatureModel();
    m.setRoot(new Feature("E-Shop"));
    return m;
  }
  
  public static FeatureModel getSimpleFm () {
    FeatureModel m = new FeatureModel();
    
    Feature car = new Feature("Car");
    m.setRoot(car);
    
    Feature motor = car.addChild("Motor", true);
    
    motor.setChildGroupKind(GroupKind.XOR);
    motor.addChild("gasoline", false);
    Feature electric = motor.addChild("electric", false);
    
    Feature comfort = car.addChild("Comfort", false);
    comfort.setChildGroupKind(GroupKind.OR);
    Feature heating = comfort.addChild("heating", false);
    comfort.addChild("entertainment", false);
    
    m.addConstraint(new CrossTreeConstraint(electric, CrossTreeConstraint.Kind.REQUIRES, heating));
    
    return m;
  }
  
}
