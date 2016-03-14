package tetris.Model;

import tetris.Helper.*;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Board {

    public final int DEFAULT_NEXT_LINE_TIME = 3;
    
    ArrayList<Line> board;
    
    public Line nextLine;
    public int timeNxtLine = DEFAULT_NEXT_LINE_TIME;
    
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
        nextLine = makeNewRandomLine(0);
    }
    
    public Line makeNewRandomLine(int numLigne){
        Line l = new Line(nbCol, numLigne);
        int i;
        for(i=1; i<=nbCol; i++) l.setBlockAtPos(i, bl.newRandomBlock(i, numLigne));
        return l;
    }

    public Line getNextLine() {
        return nextLine;
    }
    
    public Line getLineN(int nLine){
        return board.get(nLine);
    }
    
    public void setLigneN(int index, Line l){
        board.set(index, l);
    }
}
