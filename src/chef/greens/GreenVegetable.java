package chef.greens;
import chef.solutions.Vegetable;

public class GreenVegetable extends Vegetable {
    public GreenVegetable(String name, double calories) {
        super(name, calories);
        setCategory("Spicy vegetables");
    }
    public GreenVegetable(String name, double calories, double weight) {
        super(name, calories, weight);
        setCategory("Spicy vegetables");
    }
}