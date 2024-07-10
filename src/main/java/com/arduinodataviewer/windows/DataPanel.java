
package com.arduinodataviewer.windows;

/**
 *
 * @author Leandro Gómez
 * @version 0.0.1
 */
public class DataPanel extends javax.swing.JFrame {
    
    private final int WIDTH = 800;
    private final int HEIGHT = 500;
    
    public DataPanel(){
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        DataPanel window = new DataPanel();
        window.setVisible(true);
    }
}
