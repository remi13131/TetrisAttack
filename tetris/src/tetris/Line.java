package tetris;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class Line {

    ArrayList<Block> blocks;
    
    public Line(int nbCell, int numLigne){
        blocks = new ArrayList<Block>();
        int i;
        for(i=0; i<nbCell; i++) {
            blocks.add(new Block("default/default_"+numLigne+"-"+i+".png", true));
        }
    }
    
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
  
    public Block getBlockAtPos(int pos) {
        return blocks.get(pos);
    }
    
    public void setBlockAtPos(int pos, Block b) {
        blocks.set(pos, b);
    }
}
