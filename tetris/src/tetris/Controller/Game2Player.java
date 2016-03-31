package tetris.Controller;

import tetris.Helper.TetrisHelper;
import tetris.Model.*;

/**
 *
 * @author Remi
 */

public class Game2Player { 
    
    public int numActions = 0;
    public int numSec = -4;
    
    public boolean GO = false;
    
    public boolean started = false;
    
    public Board boardP1;
    public Board boardP2;
    
    public Game2Player(){
        initGame();
    }
    
    private void initGame() {
        boardP1 = new Board();
        boardP1.initGrid();
        
        boardP2 = new Board();
        boardP2.initGrid();
    }

    public void nextUpdate(){
        numActions += 1;
        if((numActions >= TetrisHelper.FPS) && !GO) {
            numSec += 1;
            numActions=0;
            if(numSec>=0){
                System.out.println("SECS : "+numSec+" "+numActions);
                nextSec();
            }
        }
        if(isStarted()){
            boardP1.getGridDown();
            boardP1.spotMatches();
            boardP1.Combo+=1;
            boardP1.updateMatchedTime();
            boardP1.killOldMatched();
            boardP1.defineEmptyLines();
            
            boardP2.getGridDown();
            boardP2.spotMatches();
            boardP2.Combo+=1;
            boardP2.updateMatchedTime();
            boardP2.killOldMatched();
            boardP2.defineEmptyLines();
        }
    }
    
    public void nextSec(){
        boardP1.timeNxtLine--;
        if(boardP1.timeNxtLine == 0) {
            insertNewLine(boardP1);
            boardP1.nextLine = boardP1.makeNewRandomLine(0);
            boardP1.timeNxtLine = boardP1.DEFAULT_NEXT_LINE_TIME;
        }
        
        boardP2.timeNxtLine--;
        if(boardP2.timeNxtLine == 0) {
            insertNewLine(boardP2);
            boardP2.nextLine = boardP2.makeNewRandomLine(0);
            boardP2.timeNxtLine = boardP2.DEFAULT_NEXT_LINE_TIME;
        }
    }
    
    public void blockExchange(Board board){
        Block b1 = board.getLineN(board.yCursor).getBlockAtPos(board.xCursor);
        Block b2 = board.getLineN(board.yCursor).getBlockAtPos(board.xCursor+1);
        if(b1.isMatched() || b2.isMatched()) return;
        board.blockExchange(b1, b2);
    }
    
    public void goLeft(Board board){
        if(board.getxCursor()>0) board.setxCursor(board.getxCursor() - 1);
    }
    
    public void goRight(Board board){
        if(board.getxCursor() < board.nbCol - 1) board.setxCursor(board.getxCursor() + 1);
    }
    
    public void goUp(Board board){
         if(board.getyCursor() < board.nbLin) board.setyCursor(board.getyCursor() + 1);
    }
    
    public void goDown(Board board){
        if(board.getyCursor()>0) board.setyCursor(board.getyCursor() - 1);
    }    
    
    public void insertNewLine(Board board){
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