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
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author Remi
 */

public class Solo extends JPanel implements ActionListener {  
    private final int DELAY = 25;
    
    private Image background;

    public int XStartBoard = 389;
    public int YStartBoard = 942;

    private int XTime = 82;
    private int YTime = 122;
    
    private Timer timer;
    
    private Game ga;
    
    public Solo(){
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
        drawBoard(g);
        drawTime(g);
    } 
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ga.nextUpdate();
        repaint();
    }
    
    public void drawBoard(Graphics g){
        Line l;
        int i, j, coordX, coordY;
        coordY = YStartBoard;
        for(i=0; i<=ga.board.nbLin; i++){
            coordY -= 69;
            coordX = XStartBoard;
            for(j=0; j<=ga.board.nbCol; j++){
                g.drawImage(ga.board.getLineN(i).getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
                coordX += 70;
            }
        }
    }
    
    public void drawTime(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 26));
        g.drawString("Time", XTime, YTime);
        g.drawString(ga.numSec+" Sec. Playing.", XTime, YTime+50);
    }
}


