package tetris.Controller;

import tetris.Model.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.ImageObserver;


/**
 *
 * @author Remi
 */

public class Game { 
    
    public int numActions = 0;
    public int numSec = 0;
    
    public int XStartBoard = 389;
    public int YStartBoard = 942;
    
    private int XTime = 82;
    private int YTime = 122;
    
    public Board board;
    
    public Game(){
        initGame();
    }
    
    private void initGame() {
        board = new Board();
        board.initGrid();
    }
    
    public void drawBoard(Graphics g, ImageObserver imgO){
        Line l;
        int i, j, coordX, coordY;
        coordY = YStartBoard;
        for(i=0; i<board.nbLin; i++){
            coordY -= 69;
            coordX = XStartBoard;
            for(j=0; j<board.nbCol; j++){
                g.drawImage(board.getLineN(i).getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, imgO);
                coordX += 70;
            }
        }
    }
    
    public void drawTime(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 26));
        g.drawString("Time", XTime, YTime);
        g.drawString(numSec+" Sec. Playing.", XTime, YTime+50);
    }

    public void nextUpdate() {
        board.getLineN(0).setBlockAtPos(1, new Block("triangleinverse.png", false));
        numActions += 1;
        if(numActions%40 == 0) {
            numSec += 1;
            System.out.println("SECS : "+(numActions/40));
            if(numSec == 10) board.getLineN(0).setBlockAtPos(0, new Block(0,0));
            if(numSec == 10) board.getLineN(1).setBlockAtPos(0, new Block("coeur.png", false));
        }
    }
    
}


