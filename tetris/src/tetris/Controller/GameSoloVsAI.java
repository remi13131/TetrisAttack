package tetris.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import tetris.Helper.TetrisHelper;
import tetris.Model.Block;
import tetris.Model.Board;
import tetris.Model.AIBoard;

/**
 *
 * @author Remi
 */
public class GameSoloVsAI {
    
    public int numActions = 0;
    public int numSec = -3;
    
    public boolean GOP1 = false;
    public boolean GOP2 = false;
    
    public boolean started = false;
    
    public Board boardP1;
    public AIBoard AIBoard;
    
    public GameSoloVsAI(){
        initGame();
    }
    
    private void initGame() {
        boardP1 = new Board();
        boardP1.initGrid();
        
        AIBoard = new AIBoard();
        AIBoard.initGrid(boardP1.getGrid());
    }

    public void nextUpdate(){
        System.out.println(""+AIBoard.ListMove);
        
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
            Méthode "l'un après l'autre"
            nextUpdateP(boardP1);
            nextUpdateP(boardP2);
*/
            
            //Méthode Thread retenue pour une exécution parralèle
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
                    nextUpdateP(AIBoard);
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
        else if(!AIBoard.getLineN(AIBoard.nbLin).isEmpty()) GameOverP2();
        else if(numSec >= 0) setStarted(true);
    }
    
    public void nextUpdateP(Board b){
        if(b.Matches.size() == 0) b.timeNxtLine -= (1000/TetrisHelper.FPS);
        if(b.getLineN(b.nbLin).isEmpty()){
            if(b.timeNxtLine <= 0) {
                b.insertNewLine();
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
    
    public void nextUpdateP(AIBoard b){
            //On fait "penser" l'AI en arrière-plan dans Thread séparé
            Thread t1;
            t1 = new Thread() { 
                @Override
                public void run() {
                    b.think();
                    b.doTheNextMove();
                }
            };
            t1.start();
        
        if(b.Matches.size() == 0) b.timeNxtLine -= (1000/TetrisHelper.FPS);
        if(b.getLineN(b.nbLin).isEmpty()){
            if(b.timeNxtLine <= 0) {
                b.insertNewLine();
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
        /*
        try {
            t1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(GameSoloVsAI.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public void blockExchange(Board board){
        board.blockExchange();
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
