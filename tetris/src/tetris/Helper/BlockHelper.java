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
            add("coeur.png");
            add("triangle.png");
            add("rond.png");
            add("etoile.png");
            add("triangleinverse.png");
            add("losange.png");
        }
    };
    
    public Block newRandomBlock(){
        int index = randomGenerator.nextInt(defaultBlocks.size());
        System.out.println(defaultBlocks.size()+" "+index);
        return new Block(defaultBlocks.get(index), false);
    }
    
}
