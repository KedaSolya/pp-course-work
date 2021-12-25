package chef.windows;

import chef.database.DbActions;
import chef.solutions.Salad;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreateRecipe {
    private final Image image = new ImageIcon("src/chef/windows/icon.png").getImage();
    private JPanel panel;
    private JRadioButton addIngredientsRadioButton;
    private JTextField textField;
    private JButton createSaladButton;
    private JTextArea textArea;

    public CreateRecipe() {
        createSaladButton.addActionListener(e -> {
            DbActions dbActions = new DbActions();
            List<String> listOfSaladsName = dbActions.getSaladsName();
            try{
                String saladName = textField.getText();
                if(saladName.equals(""))  {
                    textArea.setText("Name not entered.");
                    return;
                }
                for (String name : listOfSaladsName) {
                    if (name.equalsIgnoreCase(saladName)){
                        textArea.setText("Salad with that name already exists.");
                        return;
                    }
                }
                dbActions.saveSalad(new Salad(saladName));
                if(addIngredientsRadioButton.isSelected()) {
                    List<String> name = new ArrayList<>();
                    name.add(saladName);
                    AddIngredient addIngredient = new AddIngredient();
                    addIngredient.setListOfSalad(name);
                    addIngredient.open();
                }
                textArea.setText("Recipe successfully created.");
            }
            catch(Exception exception){ textArea.setText("failed to create recipe ");}
        });
    }

    public void open() {
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(dimension.width/2 - 200,dimension.height/2 - 200,400,400);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Create recipe");
        return jFrame;
    }
}
