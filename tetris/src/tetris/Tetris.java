package tetris;

import tetris.View.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Remi
 */

public class Tetris extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    public Tetris(String titre){  
        super(titre);
        this.initUI();
    }

    private void initUI()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1831,851);
        this.setResizable(false);
        
        Component newGameDesign;
        
        Menu m = new Menu();
        
        
        Solo tg = new Solo();
        this.add(tg, BorderLayout.CENTER);
        setLocationRelativeTo(null); 
    }

    public static void main(String[] args) {
        Tetris t = new Tetris("Tetris Attack");
        t.setVisible(true);
    }
}
