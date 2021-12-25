package chef.solutions;

import java.util.ArrayList;
import java.util.List;

public class Salad {
    private String name;
    private List<Vegetable> ingredients;
    public Salad(String name) {
        ingredients = new ArrayList<>();
        this.name =  name;
    }
    public Salad(String name,List<Vegetable> listOfIngredients){
       this.name = name;
       this.ingredients = listOfIngredients;
    }
    public List<Vegetable> getIngredients() {return ingredients;}
    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
}