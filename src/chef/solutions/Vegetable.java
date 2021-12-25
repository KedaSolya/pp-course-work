package chef.solutions;

abstract public class Vegetable {
    private String name;
    private String category;
    private double kcalPer100g;
    private double weight;
    public Vegetable(String name) {this.name = name;}
    public Vegetable(String name, double calories) {
        if (calories < 0) throw new IllegalArgumentException("Cannot create vegetable with " + calories + "kcal");
        this.name = name;
        this.kcalPer100g = calories;
        this.weight = 100;
    }
    public Vegetable(String name, double calories, double weight) {
        if (calories < 0) throw new IllegalArgumentException("Cannot create vegetable with " + calories + " kcal");
        if (weight < Double.MIN_NORMAL) throw new IllegalArgumentException("Cannot create " + weight + "grams of " + name);
        this.name = name;
        this.kcalPer100g = calories;
        this.weight = weight;
    }
    public String toString() {return (weight + " grams of " + name + " (" + category + "), " + getTotalCalories() + " kcal");}
    public String getName() {return name;}
    public double getCalories() {return kcalPer100g;}
    public double getWeight() {return weight;}
    public double getTotalCalories() {return kcalPer100g * weight / 100.0;}
    protected void setCategory(String category) {this.category = category;}
}