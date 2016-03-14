package tetris.Model;

import tetris.Helper.*;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Board {

    ArrayList<Line> board;
    
    private BlockHelper bl = new BlockHelper();
    
    public int nbCol = 5;
    public int nbLin = 11;
    
    public Board(){
        board = new ArrayList<Line>();
    }
    
    public void initGrid(){
        int i;
        for(i=0; i<=nbLin; i++) board.add(new Line(nbCol, i));
        for(i=0; i<6; i++) setLigneN(i, makeNewRandomLine(i));
    }
    
    public Line getLineN(int nLine){
        return board.get(nLine);
    }
    
    public void setLigneN(int index, Line l){
        board.set(index, l);
    }
    
    public Line makeNewRandomLine(int numLigne){
        Line l = new Line(nbCol, numLigne);
        int i;
        for(i=0; i<=nbCol; i++) l.setBlockAtPos(i, bl.newRandomBlock());
        return l;
    }
}
