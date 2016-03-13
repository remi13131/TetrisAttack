package tetris;

import java.awt.BorderLayout;
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
        this.setSize(1125,981);
        this.setResizable(false);
        
        TheGame tg = new TheGame();
        this.add(tg, BorderLayout.CENTER);
        
        setLocationRelativeTo(null); 
    }

    public static void main(String[] args) throws InterruptedException {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tetris t = new Tetris("Tetris Attack");
                t.setVisible(true);
            }
        });
    }
}
