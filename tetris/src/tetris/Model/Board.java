package tetris.Model;

import tetris.Helper.*;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Board {
    
    public ArrayList<Line> board;
    public ArrayList<ArrayList<Integer>> grid;
    
    //public ArrayList<Block> MatchedCells;
    public ArrayList<Match> Matches;
    
    public Line nextLine;
    public int timeNxtLine = TetrisHelper.DEFAULT_NEXT_LINE_TIME;
    
    public BlockHelper bl = new BlockHelper();
    
    public int nbCol = 5;
    public int nbLin = 10;
    
    public int Score;
    public int ScoreToAdd;
    public int Combo;
    
    public int xCursor;
    public int yCursor;
    
    public Board(){
        xCursor=2;
        yCursor=2;
        board = new ArrayList<Line>();
        //MatchedCells = new ArrayList<Block>();
        Matches = new ArrayList<Match>();
        Score = 0;
    }
    
    public void initGrid(){
        xCursor=2;
        yCursor=2;
        
        grid = genGrid();
        setBoard(grid);
        
        nextLine = makeNewRandomLine(0);
    }
    
    public void initGrid(ArrayList<ArrayList<Integer>> grid){
        int i;
        xCursor=2;
        yCursor=2;
        ArrayList<ArrayList<Integer>> gridCopy = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> line = new ArrayList<Integer>(grid.get(0));
        for(i=0; i<=nbLin; i++) {
            line = new ArrayList<Integer>(grid.get(i));
            gridCopy.add(line);
        }
        this.grid=gridCopy;
        setBoard(gridCopy);
        nextLine = makeNewRandomLine(0);
    }
    
    public void setBoard(ArrayList<ArrayList<Integer>> grid){
        int i, j;
        Line l;
        for(i=0; i<=nbLin; i++){
            l = new Line(nbCol, i);
            for(j=0; j<=nbCol; j++){
                if(grid.get(i).get(j)==-1) l.setBlockAtPos(j, new Block(j, i));
                else l.setBlockAtPos(j, new Block(false, grid.get(i).get(j),j, i));
            }
            board.add(l);
        }
    }
    
    public ArrayList<ArrayList<Integer>> genGrid(){
        int i, j;
        ArrayList<ArrayList<Integer>> ar = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> ai = new ArrayList<Integer>();
        do{
            ar = new ArrayList<ArrayList<Integer>>();
            for(i=0; i<5; i++) {
                ai = new ArrayList<Integer>();
                for(j=0; j<=nbCol; j++) ai.add(bl.newRandomColorWithEmpty());
                ar.add(ai);
            }
            
            ai = new ArrayList<Integer>();
            for(j=0; j<=nbCol; j++) ai.add(bl.newRandomBlockColor());    
            ar.add(ai);
            
            for(i=6; i<=nbLin; i++) {
                ai = new ArrayList<Integer>();
                for(j=0; j<=nbCol; j++) ai.add(-1);
                ar.add(ai);
            }
            
            ar = getGridDown(ar);
        
        } while(hasMatches(ar));
        return ar;
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
    

    
    public void blockExchange(){
        Block b1 = getLineN(yCursor).getBlockAtPos(xCursor);
        Block b2 = getLineN(yCursor).getBlockAtPos(xCursor+1);
        if(b1.isMatched() || b2.isMatched()) return;
        
        getLineN(yCursor).setBlockAtPos(xCursor, b2);
        getLineN(yCursor).setBlockAtPos(xCursor+1, b1);
        grid.get(yCursor).set(xCursor, b2.color);
        grid.get(yCursor).set(xCursor+1, b1.color);
    }
    
    public void getBlockDown(Block b){
        if(b.isEmpty()) return;
        if(b.isMatched()) return;
        int pos = b.getX();
        int origY = b.getY();
        int y = b.getY();

        if((y-1>=0) && getLineN(y-1).getBlockAtPos(pos).isEmpty()){ 
            y--;
            getLineN(y).setBlockAtPos(pos, b);
            getLineN(y+1).setBlockAtPos(pos, new Block(pos, y+1));
            grid.get(y).set(pos, b.color);
            grid.get(y+1).set(pos, -1);
            getBlockDown(b);
        }
    }
    
    public void getGridDown(){
        int i, j;
        for(j=0; j<=nbCol; j++){
            for(i=0; i<=nbLin; i++) getBlockDown(getLineN(i).getBlockAtPos(j));
        }
    }
    
    public ArrayList<ArrayList<Integer>> getGridDown(ArrayList<ArrayList<Integer>> list){
        ArrayList<ArrayList<Integer>> newList = new ArrayList<ArrayList<Integer>>(list);
        int i, j;
        int x, y;
        int num;
        for(j=0; j<=nbCol; j++){
            for(i=0; i<=nbLin; i++) {
                x=j;
                y=i;
                num = newList.get(y).get(x);
                while((y-1>=0) && (newList.get(y-1).get(x) == -1)){ 
                    y--;
                    newList.get(y).set(x, num);
                    newList.get(y+1).set(x, -1);
                }
            }
        }
        
        return newList;
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
    
    public boolean hasMatches(ArrayList<ArrayList<Integer>> list){
        
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
                color = list.get(y).get(x);
                
                if(color>-1){
                    //* regarder à droite de la cellule courante si la couleur est la même
                    x--;
                    while((x>=0) && (list.get(y).get(x)==color)) {
                        countHorizontalMatch++;
                        x--;
                    }

                    //* regarder à gauche de la cellule courante si la couleur est la même
                    x=j;
                    x++;
                    while((x<=nbCol) && (list.get(y).get(x)==color)) {
                        countHorizontalMatch++;
                        x++;
                    }
                    
                    x=j;
                    //* regarder en bas de la cellule courante si la couleur est la même
                    y--;
                    while((y>=0) && (list.get(y).get(x)==color)) {
                        countVerticalMatch++;
                        y--;
                    }

                    //* regarder en haut de la cellule courante si la couleur est la même
                    y=i;
                    y++;
                    while((y<=nbLin) && (list.get(y).get(x)==color)) {
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
        
        Match m;
        int combo=1;
        
        for(i=0; i<= nbLin; i++){
            for(j=0; j<=nbCol; j++){
                countHorizontalMatch = 1;
                countVerticalMatch = 1;
                
                color = getLineN(i).getBlockAtPos(j).getColor();
                
                if(color>-1){
                    //* regarder à droite de la cellule courante si la couleur est la même
                    x=j;
                    y=i;
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
                    y=i;
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
                    
                    if((countHorizontalMatch>=3)||(countVerticalMatch>=3)){
                        hadMatches = true;
                        m = new Match();
                        
                        if(countHorizontalMatch>=3) {
                            System.out.println(i+" "+j+" CH "+ countHorizontalMatch);

                            y=i;
                            x=j;

                            //* regarder à droite de la cellule courante si la couleur est la même
                            x--;
                            while((x>=0) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                                getLineN(y).getBlockAtPos(x).setMatched(true);
                                //MatchedCells.add(getLineN(y).getBlockAtPos(x));
                                m.addMatchedCell(getLineN(y).getBlockAtPos(x));
                                x--;
                            }

                            //* regarder à gauche de la cellule courante si la couleur est la même
                            x=j;
                            x++;
                            while((x<=nbCol) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                                getLineN(y).getBlockAtPos(x).setMatched(true);
                                m.addMatchedCell(getLineN(y).getBlockAtPos(x));
                                x++;
                            }
                        }

                        if(countVerticalMatch>=3) {
                            System.out.println(i+" "+j+" CV "+ countVerticalMatch);

                            y=i;
                            x=j;

                            //* regarder en bas de la cellule courante si la couleur est la même
                            y--;
                            while((y>=0) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                                getLineN(y).getBlockAtPos(x).setMatched(true);
                                m.addMatchedCell(getLineN(y).getBlockAtPos(x));
                                y--;
                            }

                            //* regarder en haut de la cellule courante si la couleur est la même
                            y=i;
                            y++;
                            while((y<=nbLin) && (getLineN(y).getBlockAtPos(x).getColor()==color) && (!getLineN(y).getBlockAtPos(x).isMatched())) {
                                getLineN(y).getBlockAtPos(x).setMatched(true);
                                m.addMatchedCell(getLineN(y).getBlockAtPos(x));
                                y++;
                            }
                        }
                        
                        y=i;
                        x=j;
                        
                        getLineN(y).getBlockAtPos(x).setMatched(true);
                        m.addMatchedCell(getLineN(y).getBlockAtPos(x));
                        
                        if(m.count>=3){
                            Matches.add(m);
                            Score += (10*(m.count-3));
                            System.out.println(" match "+combo+": "+m.count+" "+countHorizontalMatch+" "+countVerticalMatch);
                            combo+=1;
                        }
                    }
                }
            }
        }        

        return hadMatches;
    }
    
    public void updateMatchedTime(){
        int i;
        for(i=0; i<Matches.size(); i++){
            int tm = (1000/TetrisHelper.FPS);
            tm += Matches.get(i).getTimeMatched();
            Matches.get(i).setTimeMatched(tm);
        }
    }
    
    public void killOldMatched(){
        int i;
        for(i=0; i<Matches.size(); i++){
            if(Matches.get(i).getTimeMatched()>=TetrisHelper.TIME_MATCHED_SOLO){
                for(Block b : Matches.get(i).getMatchedCells()){
                    getLineN(b.getY()).setBlockAtPos(b.getX(), new Block(b.getX(), b.getY()));
                    grid.get(b.getY()).set(b.getX(), -1);
                }
                Matches.remove(Matches.get(i));
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
        ArrayList<Integer> a = new ArrayList<Integer>();
        int i;
        for(i=0; i<=nbCol; i++) a.add(l.getBlockAtPos(i).getColor());
        grid.set(index, a);
        l.updateLineNumber(index);
    }

    public void insertNewLine(){
        int i;
        boolean trouve=false;
        int indexLigne=-1;
        for(i=0; i<=nbLin; i++){
            if(getLineN(i).isEmpty()) {
                trouve=true;
                indexLigne = i;
                break;
            }
        }
        if(trouve && indexLigne != -1) {
            for(i=indexLigne; i>0; i--) 
                setLigneN(i, getLineN(i-1));
            setLigneN(0, getNextLine());
        }
    }
    
    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public ArrayList<ArrayList<Integer>> getGrid() {
        return grid;
    }
    
}
