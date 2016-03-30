package tetris.Model;

import tetris.Helper.*;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Board {

    public boolean thinking = false;
    
    public final int DEFAULT_NEXT_LINE_TIME = 4;
    
    ArrayList<Line> board;
    
    ArrayList<Block>MatchedCells;
    
    public Line nextLine;
    public int timeNxtLine = DEFAULT_NEXT_LINE_TIME;
    
    private BlockHelper bl = new BlockHelper();
    
    public int nbCol = 5;
    public int nbLin = 11;
    
    public int Score;
    public int ScoreToAdd;
    public int Combo;
    
    public int xCursor;
    public int yCursor;
    
    public Board(){
        board = new ArrayList<Line>();
        MatchedCells = new ArrayList<Block>();
        Score = 0;
    }
    
    public void initGrid(){
        int i, j;
        xCursor=2;
        yCursor=2;
        for(i=0; i<=nbLin; i++) board.add(new Line(nbCol, i));
        do{
            for(i=0; i<5; i++) setLigneN(i, makeNewRandomLineWithEmptyBlocks(i));
            setLigneN(5, makeNewRandomLine(i));
            nextLine = makeNewRandomLine(0);
            getGridDown();
        } while(hasMatches());
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
    
    public void blockExchange(Block b1, Block b2){
        int b1x=b1.getX();
        int b1y=b1.getY();
        int b2x=b2.getX();
        int b2y=b2.getY();
        getLineN(b1y).setBlockAtPos(b1x, b2);
        getLineN(b2y).setBlockAtPos(b2x, b1);
    }
    
    //#######################
    //### ANCIENNE METHODE 
    //#######################
    public void getBlockDownIterative(Block b){
        if(b.isEmpty()) return;
        int pos = b.getX();
        int origY = b.getY();
        int y = b.getY();
        //System.out.println("ppppp----"+y);
        while((y-1>=0) && getLineN(y-1).getBlockAtPos(pos).isEmpty()){ 
            y--;
            if(y != b.getY() && getLineN(y).getBlockAtPos(pos).isEmpty()) {
                getLineN(y).setBlockAtPos(pos, b);
                getLineN(y+1).setBlockAtPos(pos, new Block(pos, y+1));
            }
        }
    }
    
    public void getBlockDown(Block b){
        if(b.isEmpty()) return;
        if(b.isMatched()) return;
        int pos = b.getX();
        int origY = b.getY();
        int y = b.getY();
        //System.out.println("ppppp----"+y);
        if((y-1>=0) && getLineN(y-1).getBlockAtPos(pos).isEmpty()){ 
            y--;
            if(y != b.getY() && getLineN(y).getBlockAtPos(pos).isEmpty()) {
                getLineN(y).setBlockAtPos(pos, b);
                getLineN(y+1).setBlockAtPos(pos, new Block(pos, y+1));
            }
            
            getBlockDown(b);
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

    public boolean hasMatches(){
        
        boolean hasMatches = false;
        
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
                    
                    if((countHorizontalMatch>=3) || (countVerticalMatch>=3)) hasMatches = true;
                }
            }
        }        

        return hasMatches;
    }
    
    public boolean spotMatches(){
        
        boolean hadMatches = false;
        
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
                        hadMatches = true;
                        Score += (10*(countHorizontalMatch-2));
                        
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
                        
                        hadMatches = true;
                        
                        Score += (10*(countVerticalMatch-2));
                        
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

        return hadMatches;
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
    
    public int getxCursor() {
        return xCursor;
    }

    public int getyCursor() {
        return yCursor;
    }

    public void setxCursor(int xCursor) {
        this.xCursor = xCursor;
    }

    public void setyCursor(int yCursor) {
        this.yCursor = yCursor;
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

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public boolean isThinking() {
        return thinking;
    }
}
