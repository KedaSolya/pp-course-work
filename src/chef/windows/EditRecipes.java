package chef.windows;

import chef.database.DbActions;
import chef.solutions.Salad;
import chef.solutions.Vegetable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EditRecipes {
    private Image image = new ImageIcon("src/chef/windows/icon.png").getImage();
    private JPanel panel;
    private JComboBox comboBoxSalads;
    private JComboBox comboBoxIngredients;
    private JButton deleteRecipeButton;
    private JButton addIngredientButton;
    private JButton deleteIngredientButton;
    private JTextArea textArea;

    public EditRecipes() {
        comboBoxSalads.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                DbActions dbActions = new DbActions();
                comboBoxIngredients.removeAllItems();
                if(comboBoxSalads.getSelectedItem() == null) return;
                Salad salad =dbActions.downloadSalad(comboBoxSalads.getSelectedItem().toString());
                for(Vegetable vegetable : salad.getIngredients()) comboBoxIngredients.addItem(vegetable.getName());
            }
        });
        deleteIngredientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DbActions dbActions = new DbActions();
                    String saladName = comboBoxSalads.getSelectedItem().toString();
                    String ingredientName = comboBoxIngredients.getSelectedItem().toString();
                    dbActions.deleteIngredient(saladName,ingredientName);
                    textArea.setText("Ingredient successfully removed.");
                    fillComboBox();
                }
                catch(Exception exception){
                    textArea.setText("Failed to remove ingredient.\nYou may not have selected the ingredient.");
                }
            }
        });
        deleteRecipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                DbActions dbActions = new DbActions();
                String saladName = comboBoxSalads.getSelectedItem().toString();
                    dbActions.deleteSalad(saladName);
                    textArea.setText("Recipe successfully removed.");
                    fillComboBox();
                }
                catch(Exception exception){
                    exception.printStackTrace();
                    textArea.setText("Failed to remove recipe.");
                }
            }
        });

        addIngredientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddIngredient addIngredient = new AddIngredient();
                DbActions dbActions = new DbActions();
                addIngredient.setListOfSalad(dbActions.getSaladsName());
                addIngredient.open();
            }
        });
    }

    public void fillComboBox(){
        DbActions dbActions = new DbActions();
        comboBoxSalads.removeAllItems();
        for(String name : dbActions.getSaladsName()) comboBoxSalads.addItem(name);
    }
    public void open() {
        fillComboBox();
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(dimension.width/2 - 300,dimension.height/2 - 250,600,500);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Edit recipes");
        return jFrame;
    }
}
