package chef.compares;

import chef.solutions.Vegetable;

public class CaloriesPer100Comparator implements java.util.Comparator<Vegetable>{
    public int compare(Vegetable v1, Vegetable v2) {
        return (int) (v1.getCalories() - v2.getCalories());
    }
}