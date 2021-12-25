package chef.root;

import chef.solutions.Vegetable;

abstract public class RootVegetable extends Vegetable {
    public RootVegetable(String name, double calories) {
        super(name, calories);
        setCategory("Root vegetables");
    }

    public RootVegetable(String name, double calories, double weight) {
        super(name, calories, weight);
        setCategory("Root vegetables");
    }
}