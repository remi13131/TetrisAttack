package tetris.Model;

import tetris.Helper.*;
import static tetris.Helper.AIHelper.*;
import java.util.ArrayList;

/**
 *
 * @author Remi
 */

public class AIBoard extends Board {
    public boolean thinking = false;
    
    int numIter = 0;
    
    public ArrayList<Integer> ListMove = new ArrayList<Integer>();
    
    public int xCursorAfterMoves=0;
    public int yCursorAfterMoves=0;
    
    public AIBoard(){
        //super();
        yCursor=2;
        xCursor=2;
        
        xCursorAfterMoves = xCursor;
        yCursorAfterMoves = yCursor;
        
        
        //MoveBlockAtPosToPos(0, 1, 2);
    }
    
    public void think(){
        if(!thinking && Matches.isEmpty() && ListMove.size() <= 0){
            
            thinking = true;
            
            xCursorAfterMoves = xCursor;
            yCursorAfterMoves = yCursor;
        
            // Faire trouver une nouvelle action à faire et insérer ces actions dans la pile de mouvements à venir
            boolean trouve = false;

            int trouCouleur = -1;

            int longCombo = 0;
            int curCombo = 0;
            
            int lin=0;
            int curLin=0;
            int coul=0;
            int col=0;
            
            ArrayList<Integer> l;
            int b;

            for(coul=0; coul<BlockHelper.defaultBlocks.size(); coul++){
                if(longCombo==5) break;
                
                for(col=0;col<nbCol;col++){
                    System.out.println("col "+col);
                    trouCouleur = -1; 
                    
                    longCombo=0;
                    
                    curLin = 0;
                    
                    while(curLin<nbLin && !grid.get(curLin).isEmpty() && grid.get(curLin).get(col) != coul) {
                        curLin++;
                        if(grid.get(curLin).get(col) == coul){
                            trouve = true;
                            break;
                        }
                    }
                    
                    System.out.println("CEST CURLIN ICI" + curLin);
                    
                    if(grid.get(curLin).get(col) == coul) trouve = true;

                    if(trouve){
                        l = grid.get(curLin);
                        trouve = false;
                        for(lin = curLin; lin<nbLin; lin++){
                            System.out.println("121212121 "+curLin);
                            l = grid.get(curLin);
                            b = l.get(col);

                            if(l.isEmpty()) break;

                            if(b != coul && trouCouleur!=-1) {
                                break;
                            } else if (b != coul) {
                                trouCouleur = lin;
                                continue;
                            } else {
                                longCombo++;
                                System.out.println(""+longCombo+" "+coul+" "+b+" "+lin);
                            }

                        }
                    
                                       
                        int cll = -1;
                        int lnn = -1;
                        int cl;
                        int ln;
                        
                        System.out.println("111111111 "+longCombo+" col "+col+" lin "+curLin+" "+lin);
                        
                        if(longCombo> 3 && longCombo > curCombo && trouCouleur!=-1){
                            for(cl=0; cl<nbCol; cl++){
                                System.out.println("aaaaaaaaaaaaaaa"+cl+" "+trouCouleur);
                                if(grid.get(trouCouleur).get(cl) == coul) {
                                    cll = cl;
                                    lnn = trouCouleur;
                                    System.out.println("111111111 "+longCombo+" col "+col+" lin "+curLin+" "+lin);
                                    break;
                                }
                            }
                            
                            if(cll!=-1 && lnn != -1){
                                ListMove.clear();
                                System.out.println(" MoveBlockAtPosToPos("+lnn+", "+cll+", "+col+" CURLIN"+curLin + " LIN"+lin+" COL"+col);
                                MoveBlockAtPosToPos(lnn, cll, col);
                            }
                            
                        } else if(longCombo == 3 && longCombo > curCombo){
                            for(cl=0; cl<nbCol; cl++){
                                if(grid.get(curLin-1).get(cl) == coul){
                                    cll = cl;
                                    lnn = curLin-1;
                                    System.out.println("2222222222 "+longCombo+" col "+col+" lin "+curLin+" "+lin);
                                    break;
                                }
                                if(grid.get(lin-1).get(cl) == coul){
                                    cll = cl;
                                    lnn = curLin-1;
                                    System.out.println("3333333333 "+longCombo+" col "+col+" lin "+curLin+" "+lin);
                                    break;
                                }
                            }
                            
                            if(cll!=-1 && lnn != -1){
                                ListMove.clear();
                                System.out.println(" MoveBlockAtPosToPos("+lnn+", "+cll+", "+col+" CURLIN"+curLin + " LIN"+lin+" COL"+col);
                                MoveBlockAtPosToPos(lnn, cll, col);
                            }
                        }
                        
                    }
                    lin = 0;
                }

            }
        }
    }
    
    public void doTheNextMove(){
        numIter++;
        
        // Exécuter les mouvements écrites dans la pile de Mouvements
        
        if(((numIter * (1000/TetrisHelper.FPS)) > AIHelper.TIME_WAIT_ACTION) && (ListMove.size()>0)) {
            if(ListMove.size()>0){
                switch(ListMove.get(0)){
                    case ACTION_EXCHANGE:
                        blockExchange();
                    break;

                    case ACTION_GOLEFT:
                        if(xCursor>0) xCursor--;
                    break;

                    case ACTION_GORIGHT:
                        if(xCursor<nbCol-1) xCursor++;
                    break;

                    case ACTION_GOUP:
                        if(yCursor<nbLin) yCursor++;
                    break;

                    case ACTION_GODOWN:
                        if(yCursor>0) yCursor--;
                    break;    

                    case ACTION_NEWLINE:
                        timeNxtLine=0;
                    break;

                    default: break;
                }
            }
            ListMove.remove(ListMove.get(0));
            numIter = 0;
        }
        
        thinking = false;
    }
    
    public void MoveBlockAtPosToPos(int lnn, int cll, int col){
        if(cll<col){
            while(cll!=col){
                GoToExchange(lnn, cll);
                cll++;
            }
        }
        if(cll>col){
            while(cll!=col){
                GoToExchange(lnn, cll+1);
                cll--;
            }
        }
    }
    
    public void GoToExchange(int y, int x1){
        int i;
        
        i = yCursorAfterMoves;
        while(i<y){
            ListMove.add(ACTION_GOUP);
            i++;
        }
        
        i=yCursorAfterMoves;
        while(i>y) {
            ListMove.add(ACTION_GODOWN);
            i--;
        }
        
        i = xCursorAfterMoves;
        while(i<x1) {
            ListMove.add(ACTION_GORIGHT);
            System.out.println(""+ i + " " +x1);
            i++;
        }
        
        i = xCursorAfterMoves;
        while(i>x1) {
            ListMove.add(ACTION_GOLEFT);
            i--;
        }
        
        xCursorAfterMoves= x1<nbCol-1 ? x1 : nbCol-1;
        yCursorAfterMoves= y<nbLin ? y : nbLin;
        
        ListMove.add(ACTION_EXCHANGE);
    }
    
    public void Exchange(){
        ListMove.add(ACTION_EXCHANGE);
    }
    
}
