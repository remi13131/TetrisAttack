package tetris;

import tetris.View.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import tetris.Helper.lib.RXCardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import tetris.Helper.lib.Sound;
import tetris.Helper.lib.MiniBrowser;
import tetris.Helper.TetrisHelper;
/**
 *
 * @author Remi
 */



public class Tetris extends JFrame implements ComponentListener {
    
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
    
    public double scaleX = 0.7;
    public double scaleY = 0.7;
    
    public Tetris(){  
        super("Tetris Attack");
        
        Sound.init();
        
        pane = this.getContentPane();
        initUI();
    }

    private void initUI()
    {   
        int w = (int)(TetrisHelper.width * scaleX); 
        int h = (int)(TetrisHelper.height * scaleY);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        this.setSize(w,h);
        pane.setPreferredSize(new Dimension(w,h));
        this.pack();
        this.setResizable(true);
        this.addComponentListener(this);
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new RXCardLayout());
        //cards.setSize(w,h);
        //cards.setPreferredSize(new Dimension(w,h));
        cardLayout = (CardLayout)(cards.getLayout());
        
        Menu m = new Menu(this);
        
        cards.add(m, MENUPANEL);
        menu = m;
        
        pane.add(cards, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        
        setVisible(true);
    }

    public void newHelp(){
        MiniBrowser mB  = new MiniBrowser();
        mB.setVisible(true);
    }
    
    public void newCredits(){
        
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
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tetris t = new Tetris();
            }
        });
        //Sound.LASALADE.play();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int offsetY = this.getInsets().top;
        double scaleX = ((double)this.getWidth())/((double)TetrisHelper.width);
        double scaleY = ((double)this.getHeight()-offsetY)/((double)(TetrisHelper.height));
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
