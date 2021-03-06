package chef.compares;

import chef.solutions.Vegetable;

public class CaloriesComparator implements java.util.Comparator<Vegetable>{
    public int compare(Vegetable v1, Vegetable v2) {
        return (int) (v1.getTotalCalories() - v2.getTotalCalories());
    }
}