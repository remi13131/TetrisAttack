package tetris.Model;

import tetris.Helper.*;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Board {

    public boolean thinking = false;
    
    public final int DEFAULT_NEXT_LINE_TIME = 20;
    
    ArrayList<Line> board;
    
    ArrayList<Block>MatchedCells;
    
    public Line nextLine;
    public int timeNxtLine = DEFAULT_NEXT_LINE_TIME;
    
    private BlockHelper bl = new BlockHelper();
    
    public int nbCol = 5;
    public int nbLin = 11;
    
    public Board(){
        board = new ArrayList<Line>();
        MatchedCells = new ArrayList<Block>();
    }
    
    public void initGrid(){
        int i, j;
        for(i=0; i<=nbLin; i++) board.add(new Line(nbCol, i));
        for(i=0; i<5; i++) setLigneN(i, makeNewRandomLineWithEmptyBlocks(i));
        setLigneN(5, makeNewRandomLine(i));
        nextLine = makeNewRandomLine(0);
        getGridDown();
    }
    
    public Line makeNewRandomLineWithEmptyBlocks(int numLigne){
        Line l = new Line(nbCol, numLigne);
        int i;
        for(i=0; i<=nbCol; i++) l.setBlockAtPos(i, bl.newRandomBlockWithEmpty(i, numLigne));
        return l;
    }
    
    public Line makeNewRandomLine(int numLigne){
        Line l = new Line(nbCol, numLigne);
        int i;
        for(i=0; i<=nbCol; i++) l.setBlockAtPos(i, bl.newRandomBlock(i, numLigne));
        return l;
    }

    public void getBlockDown(Block b){
        if(b.isEmpty()) return;
        int pos = b.getX();
        int origY = b.getY();
        int y = b.getY();
        System.out.println("ppppp----"+y);
        while((y-1>=0) && getLineN(y-1).getBlockAtPos(pos).isEmpty()){ 
            y--;
            if(y != b.getY() && getLineN(y).getBlockAtPos(pos).isEmpty()) {
                getLineN(y).setBlockAtPos(pos, b);
                getLineN(y+1).setBlockAtPos(pos, new Block(pos, y+1));
            }
        }
    }
    
    public void getGridDown(){
        int i, j;
        for(j=0; j<=nbCol; j++){
            for(i=0; i<=nbLin; i++) getBlockDown(getLineN(i).getBlockAtPos(j));
        }
    }
    
    public void defineEmptyLines(){
        int i, j;
        boolean empty;
        for(i=0; i<= nbLin; i++){
            empty=true;
            for(j=0;j<=nbCol;j++){
                if(!getLineN(i).getBlockAtPos(j).isEmpty()){
                    getLineN(i).setEmpty(false);
                    empty=false;
                    break;
                }
            }
            if(empty) getLineN(i).setEmpty(true);
        }
    }
    
    public void spotMatches(){
        int i, j;
        int x=0, y;
        
        int color;
        
        int countHorizontalMatch = 0;
        int countVerticalMatch = 0;
        
        for(i=0; i<= nbLin; i++){
            y=i;
            for(j=0; j<=nbCol; j++){
                countHorizontalMatch = 1;
                countVerticalMatch = 1;
                
                y=i;
                x=j;
                color = getLineN(y).getBlockAtPos(x).getColor();
                
                if(color>-1){
                    //* regarder à droite de la cellule courante si la couleur est la même
                    x--;
                    while((x>=0) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                        countHorizontalMatch++;
                        x--;
                    }

                    //* regarder à gauche de la cellule courante si la couleur est la même
                    x=j;
                    x++;
                    while((x<=nbCol) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                        countHorizontalMatch++;
                        x++;
                    }
                    
                    x=j;
                    //* regarder en bas de la cellule courante si la couleur est la même
                    y--;
                    while((y>=0) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                        countVerticalMatch++;
                        y--;
                    }

                    //* regarder en haut de la cellule courante si la couleur est la même
                    y=i;
                    y++;
                    while((y<=nbLin) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                        countVerticalMatch++;
                        y++;
                    }
                    
                    if(countHorizontalMatch>=3) {
                        
                        System.out.println(i+" "+j+" CH "+ countHorizontalMatch);
                        
                        y=i;
                        x=j;
                        
                        getLineN(y).getBlockAtPos(x).setMatched(true);
                        MatchedCells.add(getLineN(y).getBlockAtPos(x));
                        
                        //* regarder à droite de la cellule courante si la couleur est la même
                        x--;
                        while((x>=0) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                            getLineN(y).getBlockAtPos(x).setMatched(true);
                            MatchedCells.add(getLineN(y).getBlockAtPos(x));
                            x--;
                        }

                        //* regarder à gauche de la cellule courante si la couleur est la même
                        x=j;
                        x++;
                        while((x<=nbCol) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                            getLineN(y).getBlockAtPos(x).setMatched(true);
                            MatchedCells.add(getLineN(y).getBlockAtPos(x));
                            x++;
                        }
                    }
                    
                    if(countVerticalMatch>=3) {
                        System.out.println(i+" "+j+" CV "+ countVerticalMatch);
                        y=i;
                        x=j;
                        
                        getLineN(y).getBlockAtPos(x).setMatched(true);
                        MatchedCells.add(getLineN(y).getBlockAtPos(x));
                        //* regarder en bas de la cellule courante si la couleur est la même
                        y--;
                        while((y>=0) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                            getLineN(y).getBlockAtPos(x).setMatched(true);
                            MatchedCells.add(getLineN(y).getBlockAtPos(x));
                            y--;
                        }

                        //* regarder en haut de la cellule courante si la couleur est la même
                        y=i;
                        y++;
                        while((y<=nbLin) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                            getLineN(y).getBlockAtPos(x).setMatched(true);
                            MatchedCells.add(getLineN(y).getBlockAtPos(x));
                            y++;
                        }
                    }
                }
            }
        }
    }
    
    public void updateMatchedTime(){
        int i;
        for(i=0; i<MatchedCells.size(); i++){
            MatchedCells.get(i).setTimeMatched(MatchedCells.get(i).getTimeMatched()+0.025);
        }
    }
    
    public void killOldMatched(){
        int i;
        for(i=0; i<MatchedCells.size(); i++){
            if(MatchedCells.get(i).getTimeMatched()>=3.0){
                getLineN(MatchedCells.get(i).getY()).setBlockAtPos(MatchedCells.get(i).getX(), new Block(MatchedCells.get(i).getX(), MatchedCells.get(i).getY()));
                MatchedCells.remove(MatchedCells.get(i));
            }
        }
    }
    
    public Line getNextLine() {
        return nextLine;
    }
    
    public Line getLineN(int nLine){
        return board.get(nLine);
    }
    
    public void setLigneN(int index, Line l){
        board.set(index, l);
        l.updateLineNumber(index);
    }

    public boolean isThinking() {
        return thinking;
    }
}
