package chef.windows;

import chef.database.DbActions;
import chef.solutions.Actions;
import chef.solutions.Salad;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ManagementMenu {
    private final Image image = new ImageIcon("src/chef/windows/icon.png").getImage();
    private JPanel panel;
    private JTextArea textArea;
    private JButton showIngredientsByCaloriesButton;
    private JButton showRecipeButton;
    private JButton sortIngredientsByWeightButton;
    private JButton sortIngredientsByCaloriesButton1;
    private JButton renameSaladButton;
    private JButton sortIngredientsByCaloriesButton;
    private JComboBox comboBoxSaladNames;
    private JTextField textFieldNewName;
    private JTextField textFieldMin;
    private JTextField textFieldMax;
    private JTextArea messagePanel;

    public ManagementMenu() {
        showRecipeButton.addActionListener(e -> {
            Salad salad  = getSalad();
            if (salad.getIngredients().isEmpty()) {
               messagePanel.setText("You haven't added any ingredients yet!");
               return;
            }
            textArea.setText(Actions.showRecipe(salad));
        });
        renameSaladButton.addActionListener(e -> {
            try{
                DbActions dbActions = new DbActions();
                List<String> listOfName = dbActions.getSaladsName();
                String oldName = Objects.requireNonNull(comboBoxSaladNames.getSelectedItem()).toString();
                String newName = textFieldNewName.getText();
                if(newName.equals("")) {
                    messagePanel.setText("Enter a new salad name.");
                    return;
                }
                for (String name : listOfName) {
                    if (name.equalsIgnoreCase(newName)){
                        messagePanel.setText("Salad with that name already exists.");
                        return;
                    }
                }
                dbActions.setSaladName(oldName,newName);
                comboBoxSaladNames.removeAllItems();
                fillComboBox();
                messagePanel.setText("Name changed successfully.");
            }
            catch(Exception exception) {
                messagePanel.setText("Failed to change salad name.");
            }
        });
        sortIngredientsByCaloriesButton.addActionListener(e -> {
            textArea.setText(Actions.sortIngredientsByCalories(getSalad()));
            messagePanel.setText("Successfully sorted by calories.");
        });
        sortIngredientsByCaloriesButton1.addActionListener(e -> {
            textArea.setText(Actions.sortIngredientsByCaloriesPer100(getSalad()));
            messagePanel.setText("Successfully sorted by calories per 100g.");
        });
        sortIngredientsByWeightButton.addActionListener(e -> {
            textArea.setText(Actions.sortIngredientsByWeight(getSalad()));
            messagePanel.setText("Successfully sorted by weight.");
        });
        showIngredientsByCaloriesButton.addActionListener(e -> {
            try {
                double min = Double.parseDouble(textFieldMin.getText());
                double max = Double.parseDouble(textFieldMax.getText());
                textArea.setText(Actions.showIngredientsByCalories(getSalad(), min, max));
                messagePanel.setText("Search successful.");
            } catch (Exception exception) {
                messagePanel.setText("Enter correct values.");
            }
        });
    }
    private Salad getSalad(){
        DbActions dbActions = new DbActions();
        String saladName = Objects.requireNonNull(comboBoxSaladNames.getSelectedItem()).toString();
        return dbActions.downloadSalad(saladName);
    }
    public void fillComboBox(){
        DbActions dbActions = new DbActions();
        for(String name : dbActions.getSaladsName()) comboBoxSaladNames.addItem(name);
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
        jFrame.setBounds(dimension.width/2 - 500,dimension.height/2 - 300,1000,600);
        jFrame.setResizable(true);
        jFrame.setIconImage(image);
        jFrame.setTitle("Salads management");
        return jFrame;
    }
}
