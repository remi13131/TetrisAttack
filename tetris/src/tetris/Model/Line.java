package tetris.Model;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Line {

    ArrayList<Block> blocks;
    boolean Empty;
    int y;
    int nbCells;
    
    public Line(int nbCell, int numLigne){
        blocks = new ArrayList<Block>();
        int i;
        for(i=0; i<=nbCell; i++) {
            blocks.add(new Block(i, numLigne));
        }
        System.out.println(""+blocks.size());
        Empty = true;
        y=numLigne;
        nbCells = nbCell;
    }
    
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
  
    public Block getBlockAtPos(int pos) {
        return blocks.get(pos);
    }
    
    public void setBlockAtPos(int pos, Block b) {
        blocks.set(pos, b);
        b.setY(this.y);
        b.setX(pos);
        if(!b.isEmpty()) Empty = false;
    }

    public void updateLineNumber(int newNumLigne){
        this.y=newNumLigne;
        int i;
        for(i=0; i<=nbCells; i++) getBlockAtPos(i).setY(newNumLigne);
    }
    
    public boolean isEmpty() {
        return Empty;
    }
    
}
