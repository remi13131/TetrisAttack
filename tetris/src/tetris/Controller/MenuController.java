package tetris.Controller;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class MenuController {
    
    public final int DELAY = 25;
    
    public int Choix = 1;
    public int nbChoix = 5;
    public int nbChoix2 = 13;
    
    public MenuController(){
    }
    
    public void setChoix(int i){
        if(i > nbChoix) Choix = 1;
        else if(i==0) Choix = nbChoix;
        else Choix = i;
    }
    
    public void setChoix2(int i){
        if(i > nbChoix2) Choix = 11;
        else if(i==10) Choix = nbChoix2;
        else Choix = i;
    }

    public int getChoix() {
        return Choix;
    }
    
    
}
