package tetris.View;

import java.awt.CardLayout;
import tetris.Controller.*;
import tetris.Model.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import tetris.Helper.Sound;
import tetris.Helper.TetrisHelper;

import tetris.Tetris;

/**
 *
 * @author Remi
 */

public class Solo extends JPanel implements ActionListener {  
    
    private boolean debug = false;
    
    public JPanel cards;
    
    private Image background;
    private Image backgroundBlack;
    private Image ready;
    private Image set;
    private Image go;
    private Image cursor;
    private Image pauseBg;
    private Image nxtLineHover;
    private Image bgBlackCell;
    private Image GameOverImage;
    
    public int XStartBoard = 314;
    public int YStartBoard = 820;

    private int XTime = 82;
    private int YTime = 100;
    
    private int XScore = 690;
    private int YScore = 120;
    
    private int XPressEnter = 660; 
    private int YPressEnter = 800;
    
    private int CellSizeX = 57;
    private int CellSizeY = 57;
    
    private int offsetY = 0;
    private double offsetYD = 0;
    
    private Timer timer;
    
    private GameSolo ga;
    
    boolean isPaused;
    
    boolean blinkGameOver = false;
    int blinkGameOverTime = 250;
    int countBlinkTime = 0;
    
    boolean black2p = false;
    
    public Tetris tetris;
    
    public Solo(Tetris t){
        
        tetris = t;
        
        ga = new GameSolo();
        
        timer = new Timer((1000/TetrisHelper.FPS), this);
        timer.start();
        
        this.setFocusable(true);
        addKeyListener(new TAdapter());
    
        this.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentShown( ComponentEvent e ) {
                Solo.this.requestFocusInWindow();
            }
        });
        
        initGraphics();
    }
    
    private void initGraphics(){
        loadImage();
        setDoubleBuffered(true);
    }
    
    private void loadImage() { 
        ImageIcon ii = new ImageIcon(getClass().getResource("/images/Backgrounds/1.png"));
        background = ii.getImage();
        
        ImageIcon ii7 = new ImageIcon(getClass().getResource("/images/Backgrounds/1bis.png"));
        backgroundBlack = ii7.getImage();
        
        ii = new ImageIcon(getClass().getResource("/images/Backgrounds/ready.png"));
        ready = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/images/Backgrounds/set.png"));
        set = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/images/Backgrounds/go.png"));
        go = ii.getImage();
        
        ImageIcon ii2 = new ImageIcon(getClass().getResource("/images/Backgrounds/cursor.png"));
        cursor = ii2.getImage();
        ImageIcon ii3 = new ImageIcon(getClass().getResource("/images/Backgrounds/Pause.png"));
        pauseBg = ii3.getImage();
        ImageIcon ii4 = new ImageIcon(getClass().getResource("/images/Backgrounds/nextLineBlack.png"));
        nxtLineHover = ii4.getImage();
        ImageIcon ii5 = new ImageIcon(getClass().getResource("/images/Backgrounds/bgBlackCell.png"));
        bgBlackCell = ii5.getImage();
        
        ImageIcon ii6 = new ImageIcon(getClass().getResource("/images/Backgrounds/GameOverP1.png"));
        GameOverImage = ii6.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(ga.numSec < 0){
            if(black2p) g.drawImage(backgroundBlack, 0, 0, null);
            else g.drawImage(background, 0, 0, null);
            drawBoard(g);
            drawTime(g);
            drawScore(g);
            drawCursor(g);
            drawNextLine(g);
            if(isPaused) drawPause(g);
            else drawKeyPause(g);
            switch(ga.numSec){
                case -3:
                    g.drawImage(ready, 0, 0, null);
                break;
                case -2: 
                    g.drawImage(set, 0, 0, null);
                break;
                case -1: 
                    g.drawImage(go, 0, 0, null);
                break;
                default: break;
            }
        }
        else if(!ga.GO){
            ga.setStarted(true);
            if(black2p) g.drawImage(backgroundBlack, 0, 0, null);
            else g.drawImage(background, 0, 0, null);
            drawBoard(g);
            drawTime(g);
            drawScore(g);
            drawCursor(g);
            drawNextLine(g);
            if(isPaused) drawPause(g);
            else drawKeyPause(g);
        }
        else {
            ga.setStarted(false);
            if(black2p) g.drawImage(backgroundBlack, 0, 0, null);
            else g.drawImage(background, 0, 0, null);
            drawBoard(g);
            drawTime(g);
            drawScore(g);
            drawCursor(g);
            drawNextLine(g);
            if(blinkGameOver){
                g.drawImage(GameOverImage, 0,0, this);
            }
            if(isPaused) drawPause(g);
            else drawPressEnter(g);
        }
    } 
    

    
    public void drawBoard(Graphics g){
        Line l;
        int i, j, coordX, coordY;
        coordY = YStartBoard-offsetY;
        for(i=0; i<=ga.board.nbLin; i++){
            coordY -= CellSizeY;
            coordX = XStartBoard;
            if(debug){
                g.setColor(Color.WHITE);
                g.setFont(new Font("Monospaced", Font.BOLD, 8));
                String s4 = "- EL : "+ga.board.getLineN(i).isEmpty();
                g.drawString(s4, coordX - 100, coordY+20);
            }
            for(j=0; j<=ga.board.nbCol; j++){
                if(!ga.GO && !ga.board.getLineN(i).getBlockAtPos(j).isMatched())
                    g.drawImage(ga.board.getLineN(i).getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
                else g.drawImage(ga.board.getLineN(i).getBlockAtPos(j).getBlockImageDead().getImage(), coordX, coordY, this);
                if(ga.board.getLineN(i).getBlockAtPos(j).isMatched()) g.drawImage(bgBlackCell, coordX, coordY, this);
                if(debug){
                    try{
                        String s = "- X : "+ga.board.getLineN(i).getBlockAtPos(j).getX();
                        String s1 = "- Y : "+ga.board.getLineN(i).getBlockAtPos(j).getY();
                        String s2 = "- E : "+ga.board.getLineN(i).getBlockAtPos(j).isEmpty();
                        String s3 = "- C : "+ga.board.getLineN(i).getBlockAtPos(j).getColor();
                        String s5 = "- M : "+ga.board.getLineN(i).getBlockAtPos(j).isMatched();   
                        String s6 = "- TM : "+ga.board.getLineN(i).getBlockAtPos(j).getTimeMatched();
                        
                        g.drawString(s, coordX + 10, coordY+8);
                        g.drawString(s1, coordX + 10, coordY+16);
                        g.drawString(s2, coordX + 10, coordY+24);
                        g.drawString(s3, coordX + 10, coordY+32);
                        g.drawString(s5, coordX + 10, coordY+40);
                        g.drawString(s6, coordX + 10, coordY+48);
                    }
                    catch(Exception e){System.out.println(""+e.getMessage());}
                }
                coordX += CellSizeX;
            }
        }
    }
  
    public void drawPause(Graphics g){
        g.drawImage(pauseBg, 0,0, this);
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        g.drawString("PRESS P TO RESUME GAME", (XPressEnter+100), (YPressEnter-30));
        g.drawString("PRESS ESCAPE TO GO BACK TO MENU SCREEN", XPressEnter, YPressEnter);
    }
    
    public void drawKeyPause(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        g.drawString("PRESS P TO PAUSE THE GAME", (XPressEnter+100), YPressEnter);
    }
    
    public void drawPressEnter(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        g.drawString("PRESS ENTER TO GO BACK TO MENU SCREEN", XPressEnter, YPressEnter);
    }
    
    public void drawTime(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("Time :", XTime, YTime);
        g.drawString(ga.numSec+"s Playing.", XTime, YTime+20);
    }
    
    public void drawScore(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("Score :", XScore, YScore);
        g.drawString(ga.board.getScore()+" Points", XScore, YScore+20);
    }
    
    public void drawCursor(Graphics g){
        int coordX = XStartBoard;
        int coordY = YStartBoard-CellSizeY-offsetY;
        int i;
        for(i=0; i<ga.board.getyCursor(); i++) coordY -= CellSizeY;
        for(i=0; i<ga.board.getxCursor(); i++) coordX += CellSizeX;
        g.drawImage(cursor, coordX, coordY, this);
    }
    
    public void drawNextLine(Graphics g){
        int j;
        int coordX = XStartBoard;
        int coordY = YStartBoard-offsetY;
        for(j=0; j<=ga.board.nbCol; j++){
            g.drawImage(ga.board.getNextLine().getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
            coordX += CellSizeX;
        }
        coordX = XStartBoard;
        g.drawImage(nxtLineHover, coordX, coordY, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
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
        countBlinkTime += (1000/TetrisHelper.FPS);
        if(countBlinkTime >= blinkGameOverTime){
                    countBlinkTime = 0;
                    black2p = ! black2p;
                    blinkGameOver = !blinkGameOver;
        }
        
        Solo.this.requestFocusInWindow();
        if(!ga.GO) {          
            ga.nextUpdate();
            if(ga.isStarted()){
                double percentNextLine = (TetrisHelper.DEFAULT_NEXT_LINE_TIME - ga.board.timeNxtLine);
                percentNextLine = percentNextLine / TetrisHelper.DEFAULT_NEXT_LINE_TIME;
                double value = percentNextLine * 57;
                int valueRounded = (int)Math.round(value);
                offsetY = valueRounded+1; 
                
                System.out.println(""+percentNextLine+" "+value+" "+valueRounded);
            }
            

        }
        
        repaint();
    }
    
    public void goMenu(){
         tetris.goMenu(this);
    }
    
    class TAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {

            int keycode = e.getKeyCode();
            
            if(ga.GO && (keycode == KeyEvent.VK_ENTER)) {
                timer.stop();
                goMenu();
            }
            
            if (keycode == 'p' || keycode == 'P' || (keycode == KeyEvent.VK_ESCAPE && !isPaused)) {
                pause();
                return;
            }

            if (isPaused) {
                if((keycode == KeyEvent.VK_ESCAPE)) {
                    timer.stop();
                    goMenu();
                } else {
                    return;    
                }
            }
            
            if(ga.isStarted() && !ga.GO){
                switch (keycode) {
                    case KeyEvent.VK_LEFT:
                        ga.goLeft();
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_RIGHT:
                        ga.goRight();
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_DOWN:
                        ga.goDown();
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_UP:
                        ga.goUp();
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_SPACE:
                        ga.blockExchange();
                        Sound.CHANGE_BLOCK.play();
                    break;
                }
            }
        }
    }
}


