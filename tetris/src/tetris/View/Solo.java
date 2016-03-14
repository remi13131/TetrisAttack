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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    
    private boolean debug = true;
    
    private Image background;
    private Image cursor;
    private Image pauseBg;
    private Image nxtLineHover;
    private Image bgBlackCell;
    
    public int XStartBoard = 389;
    public int YStartBoard = 942;

    private int XTime = 82;
    private int YTime = 122;
    
    private Timer timer;
    
    private GameSolo ga;
    
    boolean isPaused;
    
    public Solo(){
        ga = new GameSolo();
        
        timer = new Timer(ga.DELAY, this);
        timer.start();
        
        this.setFocusable(true);
        addKeyListener(new TAdapter());
        
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
        ImageIcon ii2 = new ImageIcon("images/Backgrounds/cursor.png");
        cursor = ii2.getImage();
        ImageIcon ii3 = new ImageIcon("images/Backgrounds/Pause.png");
        pauseBg = ii3.getImage();
        ImageIcon ii4 = new ImageIcon("images/Backgrounds/nextLineBlack.png");
        nxtLineHover = ii4.getImage();
        ImageIcon ii5 = new ImageIcon("images/Backgrounds/bgBlackCell.png");
        bgBlackCell = ii5.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        drawBoard(g);
        drawTime(g);
        drawCursor(g);
        drawNextLine(g);
        if(isPaused) g.drawImage(pauseBg, 0,0, this);
    } 
    

    
    public void drawBoard(Graphics g){
        Line l;
        int i, j, coordX, coordY;
        coordY = YStartBoard;
        for(i=0; i<=ga.board.nbLin; i++){
            coordY -= 69;
            coordX = XStartBoard;
            if(debug){
                String s4 = "- EL : "+ga.board.getLineN(i).isEmpty();
                g.drawString(s4, coordX - 100, coordY+20);
            }
            for(j=0; j<=ga.board.nbCol; j++){
                g.drawImage(ga.board.getLineN(i).getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
                if(ga.board.getLineN(i).getBlockAtPos(j).isMatched()) g.drawImage(bgBlackCell, coordX, coordY, this);
                if(debug){
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Monospaced", Font.BOLD, 10));
                    String s = "- X : "+ga.board.getLineN(i).getBlockAtPos(j).getX();
                    String s1 = "- Y : "+ga.board.getLineN(i).getBlockAtPos(j).getY();
                    String s2 = "- E : "+ga.board.getLineN(i).getBlockAtPos(j).isEmpty();
                    String s3 = "- C : "+ga.board.getLineN(i).getBlockAtPos(j).getColor();
                    String s5 = "- M : "+ga.board.getLineN(i).getBlockAtPos(j).isMatched();
                    String s6 = "- TM : "+ga.board.getLineN(i).getBlockAtPos(j).getTimeMatched();
                    g.drawString(s, coordX + 10, coordY+10);
                    g.drawString(s1, coordX + 10, coordY+20);
                    g.drawString(s2, coordX + 10, coordY+30);
                    g.drawString(s3, coordX + 10, coordY+40);
                    g.drawString(s5, coordX + 10, coordY+50);
                    g.drawString(s6, coordX + 10, coordY+60);
                }
                coordX += 70;
            }
        }
    }
    
    public void drawTime(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 26));
        g.drawString("Time", XTime, YTime);
        g.drawString(ga.numSec+"s Playing.", XTime, YTime+50);
        g.drawString(ga.numActions*5+"ms Playing.", XTime, YTime+80);
    }
    
    public void drawCursor(Graphics g){
        int coordX = XStartBoard;
        int coordY = YStartBoard-69;
        int i;
        for(i=0; i<ga.getyCursor(); i++) coordY -= 69;
        for(i=0; i<ga.getxCursor(); i++) coordX += 70;
        g.drawImage(cursor, coordX, coordY, this);
    }
    
    public void drawNextLine(Graphics g){
        int j;
        int coordX = XStartBoard;
        int coordY = YStartBoard;
        for(j=0; j<=ga.board.nbCol; j++){
            g.drawImage(ga.board.getNextLine().getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
            coordX += 70;
        }
        coordX = XStartBoard;
        g.drawImage(nxtLineHover, coordX, coordY, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("New Line in "+ga.board.timeNxtLine+" Seconds", coordX+50, coordY+50);
    }
    
    private void pause()
    {
        isPaused = !isPaused;
        if (isPaused) timer.stop();
        else timer.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ga.nextUpdate();
        repaint();
    }
    
    class TAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {

            int keycode = e.getKeyCode();

            System.out.println(""+keycode);
            
            if (keycode == 'p' || keycode == 'P') {
                pause();
                return;
            }

            if (isPaused) return;

            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    if(ga.getxCursor()>0) ga.setxCursor(ga.getxCursor() - 1);
                break;
                    
                case KeyEvent.VK_RIGHT:
                    if(ga.getxCursor() < ga.board.nbCol - 1) ga.setxCursor(ga.getxCursor() + 1);
                break;
                    
                case KeyEvent.VK_DOWN:
                    if(ga.getyCursor()>0) ga.setyCursor(ga.getyCursor() - 1);
                break;
                    
                case KeyEvent.VK_UP:
                    if(ga.getyCursor() < ga.board.nbLin) ga.setyCursor(ga.getyCursor() + 1);
                break;
                    
                case KeyEvent.VK_SPACE:
                    ga.blockExchange();
                break;
            }
        }
    }
}


