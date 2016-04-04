package tetris.Controller;

import tetris.Model.*;
import tetris.Helper.TetrisHelper;

/**
 *
 * @author Remi
 */

public class GameSolo { 
    
    public int numActions = 0;
    public int numSec = -3;
    
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
        if((numActions >= TetrisHelper.FPS) && !GO) {
            numSec += 1;
            numActions=0;
            System.out.println("SECS : "+numSec+" "+numActions);
        }
        if(isStarted()){
            if(board.getLineN(board.nbLin).isEmpty()){
                if(board.MatchedCells.size() == 0) board.timeNxtLine -= (1000/TetrisHelper.FPS);
                     
                if(board.timeNxtLine <= 0) {
                    insertNewLine();
                    board.nextLine = board.makeNewRandomLine(0);
                    board.timeNxtLine = TetrisHelper.DEFAULT_NEXT_LINE_TIME;
                    board.yCursor += 1;
                }

                board.getGridDown();
                board.spotMatches();
                board.Combo+=1;
                board.updateMatchedTime();
                board.killOldMatched();
                board.defineEmptyLines();
            }
            else GameOver();
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


