package chef.solutions;

import chef.compares.*;
import chef.cucurbitaceae.*;
import chef.greens.*;
import chef.nightshade.*;
import chef.root.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Actions {
    public static Vegetable getIngredient(String ingredientName, double weight){
        try {
            Class [] parameters = {double.class};
            if (Objects.equals(ingredientName, "Tomato") || Objects.equals(ingredientName, "Potato") || Objects.equals(ingredientName, "BellPepper"))
                ingredientName = "chef.nightshade." + ingredientName;
            if (Objects.equals(ingredientName, "Dill") || Objects.equals(ingredientName, "Parsley"))
                ingredientName = "chef.greens." + ingredientName;
            if (Objects.equals(ingredientName, "Cucumber") || Objects.equals(ingredientName, "Pumpkin"))
                ingredientName = "chef.cucurbitaceae." + ingredientName;
            if (Objects.equals(ingredientName, "Beta") || Objects.equals(ingredientName, "Carrot") || Objects.equals(ingredientName, "Onion") || Objects.equals(ingredientName, "Radish"))
                ingredientName = "chef.root." + ingredientName;
            Class ingredientClass = Class.forName(ingredientName);
            Constructor constructor = ingredientClass.getDeclaredConstructor(parameters);
            Vegetable vegetable = (Vegetable) constructor.newInstance(new Object[]{new Double(weight)});
            return vegetable;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static double countCalories(Salad salad) {
        double calories = 0.0;
        for (Vegetable vegetable : salad.getIngredients())
            calories += vegetable.getTotalCalories();
        return calories;
    }
    public static List<Vegetable> listOfIngredients(){
        List<Vegetable> listOfVegetables = new ArrayList<>();
        listOfVegetables.add(new Cucumber());
        listOfVegetables.add(new Pumpkin());
        listOfVegetables.add(new Dill());
        listOfVegetables.add(new Parsley());
        listOfVegetables.add(new BellPepper());
        listOfVegetables.add(new Potato());
        listOfVegetables.add(new Tomato());
        listOfVegetables.add(new Beta());
        listOfVegetables.add(new Carrot());
        listOfVegetables.add(new Onion());
        listOfVegetables.add(new Radish());
        return listOfVegetables;
    }
    public static String sortIngredientsByCalories(Salad salad) {
        salad.getIngredients().sort(new CaloriesComparator());
        return showRecipe(salad);
    }
    public static String sortIngredientsByCaloriesPer100(Salad salad) {
        salad.getIngredients().sort(new CaloriesPer100Comparator());
        return showRecipe(salad);
    }
    public static String sortIngredientsByWeight(Salad salad) {
        salad.getIngredients().sort(new WeightComparator());
        return showRecipe(salad);
    }
    public static String showIngredientsByCalories(Salad salad,double lowerCalories, double upperCalories) {
        double calories;
        StringBuilder builder = new StringBuilder();
        builder.append("Ingredients for calories [" + lowerCalories + ", " + upperCalories + "]\n");
        for (Vegetable vegetable : salad.getIngredients()) {
            calories = vegetable.getCalories();
            if (calories >= lowerCalories && calories <= upperCalories)
                builder.append(vegetable.getName() + ", " + vegetable.getCalories() + " kcal per 100g\n");
        }
        return builder.toString();
    }
    public static  String showRecipe(Salad salad) {
       StringBuilder builder = new StringBuilder();
       builder.append("The salad ");
       builder.append(salad.getName());
       builder.append(" contains:\n");
       for (Vegetable vegetable : salad.getIngredients()) {
           builder.append(vegetable.toString());
           builder.append("\n");
       }
       builder.append("\n");
       builder.append("----------------------------");
       builder.append("\n");
       builder.append("Total energy: " + Actions.countCalories(salad) + " kcal");
       return builder.toString();
    }
}
