package tetris.Controller;

import tetris.Helper.TetrisHelper;
import tetris.Model.*;

/**
 *
 * @author Remi
 */

public class Game2Player { 
    
    public int numActions = 0;
    public int numSec = -3;
    
    public boolean GOP1 = false;
    public boolean GOP2 = false;
    
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
        boardP2.initGrid(boardP1.getGrid());
    }

    public void nextUpdate(){
        numActions += 1;
        if((numActions >= TetrisHelper.FPS) && !(GOP1||GOP2)) {
            numSec += 1;
            numActions=0;
            if(numSec>=0){
                System.out.println("SECS : "+numSec+" "+numActions);
            }
        }
        
        if(isStarted()){
/*
            nextUpdateP(boardP1);
            nextUpdateP(boardP2);
*/
            
            Thread t1, t2;
            t1 = new Thread() { 
                @Override
                public void run() {
                    nextUpdateP(boardP1);
                }
            };
            
            t2 = new Thread() { 
                @Override
                public void run() {
                    nextUpdateP(boardP2);
                }
            };
            
            t1.start();
            t2.start();
            
            try{
                t1.join();
                t2.join();
            } 
            catch(InterruptedException e){
                System.out.println(" IIIIIIIIII "+e.getMessage());
            }
                
        } else if(!boardP1.getLineN(boardP1.nbLin).isEmpty()) GameOverP1();
        else if(!boardP2.getLineN(boardP2.nbLin).isEmpty()) GameOverP2();
        else if(numSec >= 0) setStarted(true);
    }
    
    public void nextUpdateP(Board b){
        if(b.Matches.size() == 0) b.timeNxtLine -= (1000/TetrisHelper.FPS);
        if(b.getLineN(b.nbLin).isEmpty()){
            if(b.timeNxtLine <= 0) {
                insertNewLine(b);
                b.nextLine = b.makeNewRandomLine(0);
                b.timeNxtLine = TetrisHelper.DEFAULT_NEXT_LINE_TIME;
                if(b.yCursor < b.nbLin) b.yCursor += 1;
            }
        } else if(b.timeNxtLine <= 0) setStarted(false);

        b.getGridDown();
        b.spotMatches();
        b.Combo+=1;
        b.updateMatchedTime();
        b.killOldMatched();
        b.defineEmptyLines();
    }
    
    public void blockExchange(Board board){
        Block b1 = board.getLineN(board.yCursor).getBlockAtPos(board.xCursor);
        Block b2 = board.getLineN(board.yCursor).getBlockAtPos(board.xCursor+1);
        if(b1.isMatched() || b2.isMatched()) return;
        board.blockExchange(b1, b2);
    }
    
    public boolean goLeft(Board board){
        if(board.getxCursor()>0){
            board.setxCursor(board.getxCursor() - 1);
            return true;
        } else return false;
    }
    
    public boolean goRight(Board board){
        if(board.getxCursor() < board.nbCol - 1){
            board.setxCursor(board.getxCursor() + 1);
            return true;
        } else return false;
    }
    
    public boolean goUp(Board board){
         if(board.getyCursor() < board.nbLin){
             board.setyCursor(board.getyCursor() + 1);
            return true;
        } else return false;
    }
    
    public boolean goDown(Board board){
        if(board.getyCursor()>0){
            board.setyCursor(board.getyCursor() - 1);
            return true;
        } else return false;
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
    }
    
    public void GameOverP1(){
        System.out.println("Game Over. Player 2 Wins.");
        GOP1 = true;
    }
    
    public void GameOverP2(){
        System.out.println("Game Over. Player 1 Wins.");
        GOP2 = true;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
    
}