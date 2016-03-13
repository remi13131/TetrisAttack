package tetris.Model;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Board {

    ArrayList<Line> board;
    
    public int nbCol = 5;
    public int nbLin = 11;
    
    public Board(){
        board = new ArrayList<Line>();
    }
    
    public void initGrid(){
        Line l = new Line(nbCol, 0);
        board.add(l);
        l.setBlockAtPos(0, new Block("coeur.png", false));
        int i;
        for(i=1; i<=nbLin; i++){
            board.add(new Line(nbCol, i));
        } 
    }
    
    public Line getLineN(int nLine){
        return board.get(nLine);
    }
    
    public Line makeNewRandomLine(int numLigne){
        Line l = new Line(nbCol, numLigne);
        return l;
    }
}
