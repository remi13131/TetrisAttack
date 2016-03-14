package tetris.Model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Remi
 */

public class Block {
    
    private ImageIcon blockImage;
    int x;
    int y;
    boolean Empty;
    
     public Block(int x, int y){
        ImageIcon ii = new ImageIcon("images/Blocks/defaultTransparent.png");
        this.x=x;
        this.y=y;
        blockImage = ii;
        Empty = true;
     }
    
    public Block(String path, boolean empt, int x, int y){        
        ImageIcon ii = new ImageIcon("images/Blocks/"+path);
        blockImage = ii;
        this.x=x;
        this.y=y;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
}
