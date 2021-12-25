package chef.windows;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private final Image image = new ImageIcon("src/chef/windows/icon.png").getImage();
    private JButton recipesManagementButton;
    private JPanel panel;
    private JButton createRecipeButton;
    private JButton editRecipesButton;

    public MainMenu() {
        recipesManagementButton.addActionListener(e -> {
            ManagementMenu managementMenu = new ManagementMenu();
            managementMenu.open();
        });
        editRecipesButton.addActionListener(e -> {
            EditRecipes editRecipes = new EditRecipes();
            editRecipes.open();
        });
        createRecipeButton.addActionListener(e -> {
            CreateRecipe createRecipe = new CreateRecipe();
            createRecipe.open();
        });
    }
    public void open() {
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds(dimension.width/2 - 150,dimension.height/2 - 200,300,400);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Salad Recipes");
        return jFrame;
    }
}
