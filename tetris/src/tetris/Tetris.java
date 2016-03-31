package tetris;

import tetris.View.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import tetris.Helper.RXCardLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import tetris.Helper.Sound;

/**
 *
 * @author Remi
 */



public class Tetris extends JFrame {
    
    private static final long serialVersionUID = 1L;

    public final static String MENUPANEL = "Game Menu.";
    public final static String SOLOPANEL = "Solo Game";
    public final static String TWOPLAYERPANEL = "Solo Game";
    
    CardLayout cardLayout;   
    JPanel cards; //a panel that uses CardLayout
    Container pane;
    
    Solo solo;
    TwoPlayer twoP;
    Menu menu;
    
    public Tetris(){  
        super("Tetris Attack");
        pane = this.getContentPane();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initUI();
            }
        });
    }

    private void initUI()
    {        
        //Create the panel that contains the "cards".
        cards = new JPanel(new RXCardLayout());
        cards.setSize(1831,851);
        
        cardLayout = (CardLayout)(cards.getLayout());
        
        Menu m = new Menu(this);
        
        cards.add(m, MENUPANEL);
        menu = m;
        
        pane.add(cards, BorderLayout.CENTER);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1831,851);
        this.setResizable(false);

        setLocationRelativeTo(null); 
    }

    public void newSolo(){
        
        solo = null;   
        solo = new Solo(this);
        
        cards.add(solo, Tetris.SOLOPANEL);
        cardLayout.show(cards, Tetris.SOLOPANEL);
    }
    
    public void newTwoPlayer(){
        
        twoP = null;   
        twoP = new TwoPlayer(this);
        
        cards.add(twoP, Tetris.TWOPLAYERPANEL);
        cardLayout.show(cards, Tetris.TWOPLAYERPANEL);
    }
    
    public void goMenu(Solo j){
        cards.remove(j);
        cardLayout.show(cards, MENUPANEL);
    }
    
    public void goMenu(TwoPlayer j){
        cards.remove(j);
        cardLayout.show(cards, MENUPANEL);
    }
    
    public static void main(String... args)
    {       
        Tetris t = new Tetris();
        t.setVisible(true);
        
        Sound.values();
        
        try{
            Sound.LASALADE.play();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
