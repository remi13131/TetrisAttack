package tetris;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import static java.awt.PageAttributes.MediaType.A;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Remi
 */

public class TheGame 
        extends JPanel 
        implements ActionListener { 
    
    private final int DELAY = 25;
    
    private Image background;
    
    private int XStartBoard = 389;
    private int YStartBoard = 942;
    
    private int nbCol = 5;
    private int nbLin = 11;
    
     private Timer timer;
    
    ArrayList<Line> board;
    
    public TheGame(){
        initGame();
    }
    
    private void initGame() {
        initGraphics();
        
        board = new ArrayList<Line>();
        initGrid();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    private void initGraphics(){
        loadImage();
        int w = background.getWidth(this);
        int h =  background.getHeight(this);
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(w, h));     
    }
    
    private void loadImage() { 
        ImageIcon ii = new ImageIcon("images/Backgrounds/1.png");
        background = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        drawBoard(g);
    } 
    
    public void drawBoard(Graphics g){
        Line l;
        int i, j, coordX, coordY;
        coordY = YStartBoard;
        for(i=0; i<nbLin; i++){
            coordY -= 69;
            coordX = XStartBoard;
            for(j=0; j<nbCol; j++){
                g.drawImage(board.get(i).getBlockAtPos(j).getBlockImage().getImage(), coordX, coordY, this);
                coordX += 70;
            }
        }
    }
    
    public void initGrid(){
        Line l = new Line(nbCol, 0);
        board.add(l);
        l.setBlockAtPos(0, new Block("coeur", false));
        int i, coordX, coordY;
        for(i=1; i<=nbLin; i++){
            board.add(new Line(nbCol, i));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        board.get(0).setBlockAtPos(1, new Block("triangleinverse", false));
        repaint();
    }
    
}


