package tetris.View;

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
import tetris.Controller.Game2Player;
import tetris.Helper.Sound;
import tetris.Helper.TetrisHelper;
import tetris.Model.Line;
import tetris.Tetris;

/**
 *
 * @author Remi
 */

public class TwoPlayer extends JPanel implements ActionListener {  
    
    private boolean debug = false;
    
    public JPanel cards;
    
    private Image background;
    private Image ready;
    private Image set;
    private Image go;
    private Image cursor;
    private Image pauseBg;
    private Image nxtLineHover;
    private Image bgBlackCell;
    private Image GameOverImage;
    private Image HideNewLineImage;
    
    public int XStartBoardP1 = 314;
    public int YStartBoard = 763;
    
    public int XStartBoardP2 = 1175;

    private int XTimeP1 = 82;
    private int XTimeP2 = 1588;
    private int YTime = 100;
    
    private int XScoreP1 = 690;
    private int XScoreP2 = 980;
    private int YScore = 120;
    
    private int XPressEnter = 660; 
    private int YPressEnter = 743;
    
    private int CellSizeX = 57;
    private int CellSizeY = 57;
    
    private int offsetYP1 = 0;
    private int offsetYP2 = 0;
    
    private Timer timer;
    
    private Game2Player ga;
    
    boolean isPaused;
    
    boolean blinkGameOver = false;
    int blinkGameOverTime = 250;
    int countBlinkTime = 0;
    
    public Tetris tetris;
    
    public TwoPlayer(Tetris t){
        
        tetris = t;
        
        ga = new Game2Player();
        
        timer = new Timer((1000/TetrisHelper.FPS), this);
        timer.start();
        
        this.setFocusable(true);
        addKeyListener(new TAdapter());
    
        this.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentShown( ComponentEvent e ) {
                TwoPlayer.this.requestFocusInWindow();
            }
        });
        
        initGraphics();
    }
    
    private void initGraphics(){
        loadImage();
        setDoubleBuffered(true);
    }
    
    private void loadImage() { 
        ImageIcon ii = new ImageIcon(getClass().getResource("/images/Backgrounds/2.png"));
        background = ii.getImage();
        
        ii = new ImageIcon(getClass().getResource("/images/Backgrounds/ready2P.png"));
        ready = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/images/Backgrounds/set2P.png"));
        set = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/images/Backgrounds/go2P.png"));
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
        
        ImageIcon ii7 = new ImageIcon(getClass().getResource("/images/Backgrounds/2-HideNewLine.png"));
        HideNewLineImage = ii7.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(ga.numSec < 0){
            g.drawImage(background, 0, 0, null);
            drawBoardP1(g);
            drawBoardP2(g);
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
            g.drawImage(background, 0, 0, null);
            drawBoardP1(g);
            drawBoardP2(g);
            drawTime(g);
            drawScore(g);
            drawCursor(g);
            drawNextLine(g);
            if(isPaused) drawPause(g);
            else drawKeyPause(g);
        }
        else {
            ga.setStarted(false);
            g.drawImage(background, 0, 0, null);
            drawBoardP1(g);
            drawBoardP2(g);
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
    

    
    public void drawBoardP1(Graphics g){
        Line l;
        int i, j, coordX, coordY;
        coordY = YStartBoard-offsetYP1;
        for(i=0; i<=ga.boardP1.nbLin; i++){
            coordY -= CellSizeY;
            coordX = XStartBoardP1;
            for(j=0; j<=ga.boardP1.nbCol; j++){
                if(!ga.GO) g.drawImage(ga.boardP1.getLineN(i).getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
                else g.drawImage(ga.boardP1.getLineN(i).getBlockAtPos(j).getBlockImageDead().getImage(), coordX, coordY, this);
                if(ga.boardP1.getLineN(i).getBlockAtPos(j).isMatched()) g.drawImage(bgBlackCell, coordX, coordY, this);
                coordX += CellSizeX;
            }
        }
    }
    
    public void drawBoardP2(Graphics g){
        Line l;
        int i, j, coordX, coordY;
        coordY = YStartBoard-offsetYP2;
        for(i=0; i<=ga.boardP2.nbLin; i++){
            coordY -= CellSizeY;
            coordX = XStartBoardP2;
            for(j=0; j<=ga.boardP2.nbCol; j++){
                if(!ga.GO) g.drawImage(ga.boardP2.getLineN(i).getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
                else g.drawImage(ga.boardP2.getLineN(i).getBlockAtPos(j).getBlockImageDead().getImage(), coordX, coordY, this);
                if(ga.boardP2.getLineN(i).getBlockAtPos(j).isMatched()) g.drawImage(bgBlackCell, coordX, coordY, this);
                coordX += CellSizeX;
            }
        }
    }
  
    public void drawPause(Graphics g){
        g.drawImage(pauseBg, 0,0, this);
        g.drawImage(pauseBg, 918,0, this);
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
        drawTimeP1(g);
        drawTimeP2(g);
    }
    
    public void drawTimeP1(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("Time :", XTimeP1, YTime);
        g.drawString(ga.numSec+"s Playing.", XTimeP1, YTime+20);
    }
    
    public void drawTimeP2(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("Time :", XTimeP2, YTime);
        g.drawString(ga.numSec+"s Playing.", XTimeP2, YTime+20);
    }
    
    public void drawScore(Graphics g){
        drawScoreP1(g);
        drawScoreP2(g);
    }
    
    public void drawScoreP1(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("Score :", XScoreP1, YScore);
        g.drawString(ga.boardP1.getScore()+" Points", XScoreP1, YScore+20);
    }
    
    public void drawScoreP2(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString("Score :", XScoreP2, YScore);
        g.drawString(ga.boardP2.getScore()+" Points", XScoreP2, YScore+20);
    }
    
    public void drawCursor(Graphics g){
        drawCursorP1(g);
        drawCursorP2(g);
    }
    
    public void drawCursorP1(Graphics g){
        int coordX = XStartBoardP1;
        int coordY = YStartBoard-CellSizeY-offsetYP1;
        int i;
        for(i=0; i<ga.boardP1.getyCursor(); i++) coordY -= CellSizeY;
        for(i=0; i<ga.boardP1.getxCursor(); i++) coordX += CellSizeX;
        g.drawImage(cursor, coordX-2, coordY-2, this);
    }
        
    public void drawCursorP2(Graphics g){
        int coordX = XStartBoardP2;
        int coordY = YStartBoard-CellSizeY-offsetYP2;
        int i;
        for(i=0; i<ga.boardP2.getyCursor(); i++) coordY -= CellSizeY;
        for(i=0; i<ga.boardP2.getxCursor(); i++) coordX += CellSizeX;
        g.drawImage(cursor, coordX-2, coordY-2, this);
    }
    
    public void drawNextLine(Graphics g){
        drawNextLineP1(g);
        drawNextLineP2(g);
        g.drawImage(HideNewLineImage, 0, 0, this);
    }
    
    public void drawNextLineP1(Graphics g){
        int j;
        int coordX = XStartBoardP1;
        int coordY = YStartBoard-offsetYP1;
        for(j=0; j<=ga.boardP1.nbCol; j++){
            g.drawImage(ga.boardP1.getNextLine().getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
            coordX += CellSizeX;
        }
        coordX = XStartBoardP1;
        g.drawImage(nxtLineHover, coordX, coordY, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
    }

    public void drawNextLineP2(Graphics g){
        int j;
        int coordX = XStartBoardP2;
        int coordY = YStartBoard-offsetYP2;
        for(j=0; j<=ga.boardP2.nbCol; j++){
            g.drawImage(ga.boardP2.getNextLine().getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
            coordX += CellSizeX;
        }
        coordX = XStartBoardP2;
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
                    blinkGameOver = !blinkGameOver;
        }
        TwoPlayer.this.requestFocusInWindow();
        
        if(!ga.GO) {
            ga.nextUpdate();
            double percentNextLine = (TetrisHelper.DEFAULT_NEXT_LINE_TIME - ga.boardP1.timeNxtLine);
            percentNextLine = percentNextLine / TetrisHelper.DEFAULT_NEXT_LINE_TIME;
            double value = percentNextLine * 57;
            int valueRounded = (int)Math.round(value);
            offsetYP1 = valueRounded-1;

            percentNextLine = (TetrisHelper.DEFAULT_NEXT_LINE_TIME - ga.boardP2.timeNxtLine);
            percentNextLine = percentNextLine / TetrisHelper.DEFAULT_NEXT_LINE_TIME;
            value = percentNextLine * 57;
            valueRounded = (int)Math.round(value);
            offsetYP2 = valueRounded-1; 

            System.out.println(""+percentNextLine+" "+value+" "+valueRounded);
            
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
                    
                    case KeyEvent.VK_Q:
                        ga.goLeft(ga.boardP1);
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_D:
                        ga.goRight(ga.boardP1);
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_S:
                        ga.goDown(ga.boardP1);
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_Z:
                        ga.goUp(ga.boardP1);
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_SPACE:
                        ga.blockExchange(ga.boardP1);
                        Sound.CHANGE_BLOCK.play();
                    break;
                    
                    case KeyEvent.VK_LEFT:
                        ga.goLeft(ga.boardP2);
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_RIGHT:
                        ga.goRight(ga.boardP2);
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_DOWN:
                        ga.goDown(ga.boardP2);
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_UP:
                        ga.goUp(ga.boardP2);
                        Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_ENTER:
                        ga.blockExchange(ga.boardP2);
                        Sound.CHANGE_BLOCK.play();
                    break;
                        
                    default: break;
                }
            }
        }
    }
}
