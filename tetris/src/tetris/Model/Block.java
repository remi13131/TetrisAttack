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
    private ImageIcon blockImageDead;
    int x;
    int y;
    int color;
    boolean Empty;
    boolean Matched=false;
    int TimeMatched=0;
    
     public Block(int x, int y){
        ImageIcon ii = new ImageIcon(getClass().getResource("/images/Blocks/defaultTransparent.png"));
        this.x=x;
        this.y=y;
        blockImage = ii;
        blockImageDead = ii;
        Empty = true;
        color = -1;
     }
    
    public Block(String path, boolean empt, int Color, int x, int y){        
        ImageIcon ii = new ImageIcon(getClass().getResource("/images/Blocks/"+path+".png"));
        ImageIcon ii2 = new ImageIcon(getClass().getResource("/images/Blocks/"+path+"mort.png"));
        blockImage = ii;
        blockImageDead = ii2;
        this.x=x;
        this.y=y;
        Empty = empt;
        this.color = Color;
    }

    public ImageIcon getBlockImage() {
        return blockImage;
    }

    public ImageIcon getBlockImageDead() {
        return blockImageDead;
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

    public int getColor() {
        return color;
    }

    public boolean isMatched() {
        return Matched;
    }

    public void setMatched(boolean Matched) {
        this.Matched = Matched;
    }

    public int getTimeMatched() {
        return TimeMatched;
    }

    public void setTimeMatched(int TimeMatched) {
        this.TimeMatched = TimeMatched;
    }
    
    
    
}
