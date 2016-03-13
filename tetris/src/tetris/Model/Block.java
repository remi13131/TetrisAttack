package tetris.Model;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Remi
 */

public class Block {
    
    private ImageIcon blockImage;
    boolean Empty;
    
     public Block(int x, int y){
        ImageIcon ii = new ImageIcon("images/Blocks/default/default_"+y+"-"+x+".png");
        blockImage = ii;
        Empty = true;
     }
    
    public Block(String path, boolean empt){        
        ImageIcon ii = new ImageIcon("images/Blocks/"+path);
        blockImage = ii;
        Empty = empt;
    }

    public ImageIcon getBlockImage() {
        return blockImage;
    }

    public boolean isEmpty() {
        return Empty;
    }

    public void setEmpty(boolean isEmpty) {
        this.Empty = isEmpty;
    }
    
}
