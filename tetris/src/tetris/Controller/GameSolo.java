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

public class GameSolo { 
    
    public final int DELAY = 25;
    
    public int numActions = 0;
    public int numSec = -4;
    
    public boolean GO = false;
    
    public boolean started = false;
    
    public Board board;
    
    public GameSolo(){
        initGame();
    }
    
    private void initGame() {
        board = new Board();
        board.initGrid();
    }

    public void nextUpdate(){
        numActions += 1;
        int nbActionParSeconde = 1000/DELAY;
        if(numActions%nbActionParSeconde == 0) { // Toutes les secondes (1 action = 25ms, donc 40*25 = 1000ms = 1sec)
            numSec += 1;
            if(numSec>=0){
                System.out.println("SECS : "+(numActions/nbActionParSeconde));
                nextSec();
                //if(numSec == 10) board.getLineN(0).setBlockAtPos(0, new Block(0,0));
                //if(numSec == 10) board.getLineN(1).setBlockAtPos(0, new Block("coeur.png", false));
            }
        }
        if(isStarted()){
            board.spotMatches();
            board.Combo+=1;
            board.updateMatchedTime();
            board.killOldMatched();
            board.defineEmptyLines();
            board.getGridDown();
        }
    }
    
    public void nextSec(){
        board.timeNxtLine--;
        if(board.timeNxtLine == 0) {
            insertNewLine();
            board.nextLine = board.makeNewRandomLine(0);
            board.timeNxtLine = board.DEFAULT_NEXT_LINE_TIME;
        }
    }
    
    public void blockExchange(){
        Block b1 = board.getLineN(board.yCursor).getBlockAtPos(board.xCursor);
        Block b2 = board.getLineN(board.yCursor).getBlockAtPos(board.xCursor+1);
        if(b1.isMatched() || b2.isMatched()) return;
        board.blockExchange(b1, b2);
    }
    
    public void goLeft(){
        if(board.getxCursor()>0) board.setxCursor(board.getxCursor() - 1);
    }
    
    public void goRight(){
        if(board.getxCursor() < board.nbCol - 1) board.setxCursor(board.getxCursor() + 1);
    }
    
    public void goUp(){
         if(board.getyCursor() < board.nbLin) board.setyCursor(board.getyCursor() + 1);
    }
    
    public void goDown(){
        if(board.getyCursor()>0) board.setyCursor(board.getyCursor() - 1);
    }    
    
    public void insertNewLine(){
        int i;
        boolean trouve=false;
        int indexLigne=-1;
        for(i=0; i<=board.nbLin; i++){
            if(board.getLineN(i).isEmpty()) {
                trouve=true;
                indexLigne = i;
                break;
            }
        }
        if(trouve && indexLigne != -1) {
            for(i=indexLigne; i>0; i--){
                board.setLigneN(i, board.getLineN(i-1));
                board.getLineN(i);
            }
            board.setLigneN(0, board.getNextLine());
        }
        else GameOver();
    }
    
    public void GameOver(){
        System.out.println("Game Over.");
        GO = true;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
}


