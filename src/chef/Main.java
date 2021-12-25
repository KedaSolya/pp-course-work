package chef;

import chef.windows.MainMenu;
import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        MainMenu menu = new MainMenu();
        menu.open();
    }
}