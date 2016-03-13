package tetris.View;

import tetris.Controller.*;
import tetris.Model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import static java.awt.PageAttributes.MediaType.A;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author Remi
 */

public class TheGame 
        extends JPanel 
        implements ActionListener { 
    
    private final int DELAY = 25;
    
    private Image background;
    
    private Timer timer;
    
    private Game ga;
    
    public TheGame(){
        ga = new Game();
        
        timer = new Timer(DELAY, this);
        timer.start();
        
        initGraphics();
    }
    
    private void initGraphics(){
        loadImage();
        int w = background.getWidth(this);
        int h =  background.getHeight(this);
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(w, h));     
    }
    
    private void loadImage() { 
        ImageIcon ii = new ImageIcon("images/Backgrounds/1.png");
        background = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        ga.drawBoard(g, this);
        ga.drawTime(g);
    } 
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ga.nextUpdate();
        repaint();
    }
    
}


