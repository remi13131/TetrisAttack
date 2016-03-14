package tetris.Model;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Line {

    ArrayList<Block> blocks;
    boolean Empty;
    
    public Line(int nbCell, int numLigne){
        blocks = new ArrayList<Block>();
        int i;
        for(i=0; i<=nbCell; i++) {
            blocks.add(new Block(i, numLigne));
        }
        System.out.println(""+blocks.size());
        Empty = true;
    }
    
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
  
    public Block getBlockAtPos(int pos) {
        return blocks.get(pos);
    }
    
    public void setBlockAtPos(int pos, Block b) {
        blocks.set(pos, b);
        if(!b.isEmpty()) Empty = false;
    }

    public boolean isEmpty() {
        return Empty;
    }
    
}
