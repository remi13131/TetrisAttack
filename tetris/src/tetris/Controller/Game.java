package tetris.Controller;

import tetris.Model.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.List;


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

    public void nextUpdate(){
        board.getLineN(0).setBlockAtPos(1, new Block("triangleinverse.png", false));
        numActions += 1;
        if(numActions%40 == 0) { // Toutes les secondes (1 action = 25ms, donc 40*25 = 1000ms = 1sec)
            numSec += 1;
            System.out.println("SECS : "+(numActions/40));
            //if(numSec == 10) board.getLineN(0).setBlockAtPos(0, new Block(0,0));
            //if(numSec == 10) board.getLineN(1).setBlockAtPos(0, new Block("coeur.png", false));
        }
    }
    
    public void nextSec(){
        
    }
    
}


