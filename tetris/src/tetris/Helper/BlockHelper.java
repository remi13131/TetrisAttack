package tetris.Helper;

import java.awt.Image;
import tetris.Model.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Remi
 */
public class BlockHelper {

    private Random randomGenerator = new Random();
    
    static public ArrayList<String> defaultBlocks = new ArrayList<String>() {
        {
            add("coeur"); //numCouleur = 0
            add("triangle"); //numCouleur = 1
            add("rond"); //numCouleur = 2
            add("etoile"); //numCouleur = 3
            add("triangleinverse"); //numCouleur = 4
            add("losange"); //numCouleur = 5
        }
    };
    
    public ArrayList<Image> blockImages = new ArrayList<Image>() {
        {
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/coeur.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/triangle.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/rond.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/etoile.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/triangleinverse.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/losange.png")).getImage());
        }
    };
    
    public ArrayList<Image> deadBlockImages = new ArrayList<Image>() {
        {
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/coeurmort.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/trianglemort.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/rondmort.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/etoilemort.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/triangleinversemort.png")).getImage());
            add(new ImageIcon(getClass().getResource("/ressources/images/Blocks/losangemort.png")).getImage());
        }
    };

/*
    public List<Image> BlinkblockImages = new ArrayList<Image>() {
        {
            add(new ImageIcon(getClass().getResource("/images/Blocks/coeurblink.png")).getImage());
            add(new ImageIcon(getClass().getResource("/images/Blocks/coeurblink.png")).getImage());
            add(new ImageIcon(getClass().getResource("/images/Blocks/coeurblink.png")).getImage());
            add(new ImageIcon(getClass().getResource("/images/Blocks/coeurblink.png")).getImage());
            add(new ImageIcon(getClass().getResource("/images/Blocks/coeurblink.png")).getImage());
            add(new ImageIcon(getClass().getResource("/images/Blocks/coeurblink.png")).getImage());
        }
    };
*/
    
    public Block newRandomBlock(int lin, int col){
        int index = randomGenerator.nextInt(defaultBlocks.size());
        return new Block(false, index, col, lin);
    }
    
    public Block newRandomBlockWithEmpty(int lin, int col){
        int index = randomGenerator.nextInt(defaultBlocks.size()+2);
        if(index >= defaultBlocks.size()) return new Block(col, lin);
        else return new Block(false, index, col, lin);
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
