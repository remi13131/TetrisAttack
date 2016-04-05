package tetris.Helper;

import tetris.Model.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Remi
 */
public class BlockHelper {

    private Random randomGenerator = new Random();
    
    public List<String> defaultBlocks = new ArrayList<String>() {
        {
            add("coeur");
            add("triangle");
            add("rond");
            add("etoile");
            add("triangleinverse");
            add("losange");
        }
    };
    
    public Block newRandomBlock(int lin, int col){
        int index = randomGenerator.nextInt(defaultBlocks.size());
        return new Block(defaultBlocks.get(index), false, index, col, lin);
    }
    
    public Block newRandomBlockWithEmpty(int lin, int col){
        int index = randomGenerator.nextInt(defaultBlocks.size()+2);
        if(index >= defaultBlocks.size()) return new Block(col, lin);
        else return new Block(defaultBlocks.get(index), false, index,col, lin);
    }
    
    public int newRandomBlockColor(){
        return randomGenerator.nextInt(defaultBlocks.size());
    }
    
    public int newRandomColorWithEmpty(){
        int index = randomGenerator.nextInt(defaultBlocks.size()+2);
        if(index >= defaultBlocks.size()) return -1;
        else return index;
    }
    
}
