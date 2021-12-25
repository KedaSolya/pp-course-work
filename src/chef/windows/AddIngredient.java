package chef.windows;

import chef.database.DbActions;
import chef.solutions.Actions;
import chef.solutions.Vegetable;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class AddIngredient {
    private Image image = new ImageIcon("src/chef/windows/icon.png").getImage();
    private JPanel panel;
    private JTextArea textArea;
    private JComboBox comboBoxSalads;
    private JComboBox comboBoxIngredients;
    private JTextArea messageText;
    private JButton addIngredientButton;
    private JTextField weightField;
    private List<String> listOfSaladsName;
    public AddIngredient() {
        addIngredientButton.addActionListener(e -> {
            try {
                DbActions dbActions = new DbActions();
                String saladName = Objects.requireNonNull(comboBoxSalads.getSelectedItem()).toString();
                String ingredientName = Objects.requireNonNull(comboBoxIngredients.getSelectedItem()).toString();
                double weight = Double.parseDouble(weightField.getText());
                Vegetable vegetable = Actions.getIngredient(ingredientName, weight);
                int saladId = dbActions.getSaladId(saladName);
                dbActions.saveIngredient(vegetable, saladId);
                messageText.setText("Ingredient successfully added.");
            }catch(Exception exception) {messageText.setText("Fail. Check the entered data.");}
        });
    }
    public void fillComboBoxSalads(){
        if(listOfSaladsName == null) return;
        for(String name : listOfSaladsName) comboBoxSalads.addItem(name);
    }
    public void setListOfSalad(List<String> listOfSalad){
        this.listOfSaladsName = listOfSalad;
    }
    public void fillListOfIngredients(){
        List<Vegetable> list = Actions.listOfIngredients();
        StringBuilder builder = new StringBuilder();
        builder.append("\tList of available ingredients\n");
        for(Vegetable vegetable : list){
            builder.append(vegetable);
            builder.append("\n");
        }
        textArea.setText(builder.toString());
    }
    public void fillComboBoxIngredients(){
        List<Vegetable> list = Actions.listOfIngredients();
        for(Vegetable vegetable : list) comboBoxIngredients.addItem(vegetable.getName());
    }

    public void open() {
        fillListOfIngredients();
        fillComboBoxIngredients();
        fillComboBoxSalads();
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(dimension.width/2 - 250,dimension.height/2 - 250,500,500);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Add ingredient");
        return jFrame;
    }
}
