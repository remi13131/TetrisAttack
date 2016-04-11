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
    
    int x;
    int y;
    int color;
    boolean Empty;
    boolean Matched=false;
    boolean isDead=false;
    boolean isBlinking=false;
    
     public Block(int x, int y){
        this.x=x;
        this.y=y;
        Empty = true;
        color = -1;
     }
    
    public Block(boolean empt, int Color, int x, int y){ 
        this.x=x;
        this.y=y;
        Empty = empt;
        this.color = Color;
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
}
