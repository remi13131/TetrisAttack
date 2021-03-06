/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import tetris.Controller.GameSoloVsAI;
import tetris.Helper.TetrisHelper;
import tetris.Helper.lib.Sound;
import tetris.Model.Line;
import tetris.Tetris;

/**
 *
 * @author Remi
 */
public class SoloVsAI extends JPanel implements ActionListener {

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
    private Image GameOverImageP1;
    private Image GameOverImageP2;
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
    
    private GameSoloVsAI ga;
    
    boolean isPaused;
    
    boolean blinkGameOver = false;
    int blinkGameOverTime = 250;
    int countBlinkTime = 0;
    
    public Tetris tetris;
    
    public SoloVsAI(Tetris t){
        
        tetris = t;
        
        ga = new GameSoloVsAI();
        
        timer = new Timer((1000/TetrisHelper.FPS), this);
        timer.start();
        
        this.setFocusable(true);
        addKeyListener(new TAdapter());
    
        this.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentShown( ComponentEvent e ) {
                SoloVsAI.this.requestFocusInWindow();
            }
        });
        
        initGraphics();
    }
    
    private void initGraphics(){
        loadImage();
        setDoubleBuffered(true);
    }
    
    private void loadImage() { 
        ImageIcon ii = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/2.png"));
        background = ii.getImage();
        
        ii = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/ready2P.png"));
        ready = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/set2P.png"));
        set = ii.getImage();
        ii = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/go2P.png"));
        go = ii.getImage();
        
        ImageIcon ii2 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/cursor.png"));
        cursor = ii2.getImage();
        ImageIcon ii3 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/Pause.png"));
        pauseBg = ii3.getImage();
        ImageIcon ii4 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/nextLineBlack.png"));
        nxtLineHover = ii4.getImage();
        ImageIcon ii5 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/bgBlackCell.png"));
        bgBlackCell = ii5.getImage();
        
        ImageIcon ii6 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/GameOverP1.png"));
        GameOverImageP1 = ii6.getImage();
        
        ImageIcon ii8 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/GameOverP2.png"));
        GameOverImageP2 = ii8.getImage();
        
        ImageIcon ii7 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/2-HideNewLine.png"));
        HideNewLineImage = ii7.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(tetris.scaleX, tetris.scaleY);
        
        if(ga.numSec < 0){
            g2d.drawImage(background, 0, 0, null);
            drawBoardP1(g2d);
            drawBoardP2(g2d);
            drawTime(g2d);
            drawScore(g2d);
            drawCursor(g2d);
            drawNextLine(g2d);
            if(isPaused) drawPause(g2d);
            else drawKeyPause(g2d);
            switch(ga.numSec){
                case -3:
                    g2d.drawImage(ready, 0, 0, null);
                break;
                case -2: 
                    g2d.drawImage(set, 0, 0, null);
                break;
                case -1: 
                    g2d.drawImage(go, 0, 0, null);
                break;
                default: break;
            }
        }
        else if(!(ga.GOP1||ga.GOP2)){
            g2d.drawImage(background, 0, 0, null);
            drawBoardP1(g2d);
            drawBoardP2(g2d);
            drawTime(g2d);
            drawScore(g2d);
            drawCursor(g2d);
            drawNextLine(g2d);
            if(isPaused) drawPause(g2d);
            else drawKeyPause(g2d);
        }
        else {
            g2d.drawImage(background, 0, 0, null);
            drawBoardP1(g2d);
            drawBoardP2(g2d);
            drawTime(g2d);
            drawScore(g2d);
            drawCursor(g2d);
            drawNextLine(g2d);
            if(blinkGameOver){
                if(ga.GOP1) g2d.drawImage(GameOverImageP1, 0,0, this);
                if(ga.GOP2) g2d.drawImage(GameOverImageP2, 0,0, this);
            }
            if(isPaused) drawPause(g2d);
            else drawPressEnter(g2d);
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
                if(ga.boardP1.getLineN(i).getBlockAtPos(j).getColor() != -1){
                    if(!ga.GOP1) g.drawImage(ga.boardP1.bl.blockImages.get(ga.boardP1.getLineN(i).getBlockAtPos(j).getColor()), coordX, coordY, this);
                    else g.drawImage(ga.boardP1.bl.deadBlockImages.get(ga.boardP1.getLineN(i).getBlockAtPos(j).getColor()), coordX, coordY, this);
                    if(ga.boardP1.getLineN(i).getBlockAtPos(j).isMatched()) g.drawImage(bgBlackCell, coordX, coordY, this);
                }
                coordX += CellSizeX;
            }
        }
    }
    
    public void drawBoardP2(Graphics g){
        Line l;
        int i, j, coordX, coordY;
        coordY = YStartBoard-offsetYP2;
        for(i=0; i<=ga.AIBoard.nbLin; i++){
            coordY -= CellSizeY;
            coordX = XStartBoardP2;
            for(j=0; j<=ga.AIBoard.nbCol; j++){
                    if(ga.AIBoard.getLineN(i).getBlockAtPos(j).getColor() != -1){
                    if(!ga.GOP2) g.drawImage(ga.AIBoard.bl.blockImages.get(ga.AIBoard.getLineN(i).getBlockAtPos(j).getColor()), coordX, coordY, this);
                    else g.drawImage(ga.AIBoard.bl.deadBlockImages.get(ga.AIBoard.getLineN(i).getBlockAtPos(j).getColor()), coordX, coordY, this);
                    if(ga.AIBoard.getLineN(i).getBlockAtPos(j).isMatched()) g.drawImage(bgBlackCell, coordX, coordY, this);
                }
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
        g.drawString(ga.AIBoard.getScore()+" Points", XScoreP2, YScore+20);
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
        for(i=0; i<ga.AIBoard.getyCursor(); i++) coordY -= CellSizeY;
        for(i=0; i<ga.AIBoard.getxCursor(); i++) coordX += CellSizeX;
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
            g.drawImage(ga.boardP1.bl.blockImages.get(ga.boardP1.getNextLine().getBlockAtPos(j).getColor()), coordX, coordY, this);
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
        for(j=0; j<=ga.AIBoard.nbCol; j++){
            g.drawImage(ga.AIBoard.bl.blockImages.get(ga.AIBoard.getNextLine().getBlockAtPos(j).getColor()), coordX, coordY, this);
            coordX += CellSizeX;
        }
        coordX = XStartBoardP2;
        g.drawImage(nxtLineHover, coordX, coordY, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        if(!isPaused){
            countBlinkTime += (1000/TetrisHelper.FPS);
            if(countBlinkTime >= blinkGameOverTime){
                        countBlinkTime = 0;
                        blinkGameOver = !blinkGameOver;
            }
            SoloVsAI.this.requestFocusInWindow();

            if(!(ga.GOP1||ga.GOP2)) {
                ga.nextUpdate();
                if(ga.isStarted()){
                    double percentNextLine = (TetrisHelper.DEFAULT_NEXT_LINE_TIME - ga.boardP1.timeNxtLine);
                    percentNextLine = percentNextLine / TetrisHelper.DEFAULT_NEXT_LINE_TIME;
                    double value = percentNextLine * 57;
                    int valueRounded = (int)Math.round(value);
                    offsetYP1 = valueRounded+1; 

                    percentNextLine = (TetrisHelper.DEFAULT_NEXT_LINE_TIME - ga.AIBoard.timeNxtLine);
                    percentNextLine = percentNextLine / TetrisHelper.DEFAULT_NEXT_LINE_TIME;
                    value = percentNextLine * 57;
                    valueRounded = (int)Math.round(value);
                    offsetYP2 = valueRounded+1; 
                }
            }
        }
        paintImmediately(this.getBounds());//repaint();
    }
    
    public void goMenu(){
         tetris.goMenu(this);
    }
    
    class TAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {

            int keycode = e.getKeyCode();
            
            if((ga.GOP1||ga.GOP2) && (keycode == KeyEvent.VK_ENTER)) {
                timer.stop();
                goMenu();
            }
            
            if (keycode == 'p' || keycode == 'P' || (keycode == KeyEvent.VK_ESCAPE && !isPaused)) {
                isPaused = !isPaused;
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
            
            if(ga.isStarted() && !(ga.GOP1||ga.GOP2)){
                switch (keycode) {
                    
                    case KeyEvent.VK_LEFT:
                        if(ga.goLeft(ga.boardP1)) Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_RIGHT:
                        if(ga.goRight(ga.boardP1)) Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_DOWN:
                        if(ga.goDown(ga.boardP1)) Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_UP:
                        if(ga.goUp(ga.boardP1)) Sound.MOVE.play();
                    break;

                    case KeyEvent.VK_SPACE:
                        ga.blockExchange(ga.boardP1);
                        Sound.CHANGE_BLOCK.play();
                    break;
                        
                    case KeyEvent.VK_TAB:
                        if(ga.boardP1.getLineN(10).isEmpty()) ga.boardP1.timeNxtLine = 0;
                    break;
                        
                    default: break;
                }
            }
        }
    }
}
