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
            if(board.Matches.size() == 0) board.timeNxtLine -= (1000/TetrisHelper.FPS);
            if(board.getLineN(board.nbLin).isEmpty()){
                if(board.timeNxtLine <= 0) {
                    insertNewLine();
                    board.nextLine = board.makeNewRandomLine(0);
                    board.timeNxtLine = TetrisHelper.DEFAULT_NEXT_LINE_TIME;
                    if(board.yCursor < board.nbLin) board.yCursor += 1;
                }
            } else if(board.timeNxtLine <= 0) setStarted(false);
            
            board.getGridDown();
            board.spotMatches();
            board.Combo+=1;
            board.updateMatchedTime();
            board.killOldMatched();
            board.defineEmptyLines();
            
        } else if(!board.getLineN(board.nbLin).isEmpty()) GameOver();
        else if(numSec >= 0) setStarted(true);
    }
    
    public void blockExchange(){
        board.blockExchange();
    }
    
    public boolean goLeft(){
        if(board.getxCursor()>0){
            board.setxCursor(board.getxCursor() - 1);
            return true;
        } else return false;
    }
    
    public boolean goRight(){
        if(board.getxCursor() < board.nbCol - 1){
            board.setxCursor(board.getxCursor() + 1);
            return true;
        } else return false;
    }
    
    public boolean goUp(){
         if(board.getyCursor() < board.nbLin){
             board.setyCursor(board.getyCursor() + 1);
            return true;
        } else return false;
    }
    
    public boolean goDown(){
        if(board.getyCursor()>0){
            board.setyCursor(board.getyCursor() - 1);
            return true;
        } else return false;
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


