package tetris.View;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import tetris.Controller.MenuController;
import tetris.Helper.lib.Sound;
import tetris.Tetris;

/**
 *
 * @author Remi
 */
public class Menu  extends JPanel implements ActionListener {
    
    private Image backgroundNoir;
    private Image backgroundInsertCoin;
    private Image menu0;
    private Image menu1;
    private Image menu2;
    private Image menu3;
    private Image menu4;
    private Image menu5;
    private Image menu11;
    private Image menu12;
    private Image menu13;
    
    private MenuController ga;
    private Timer timer;
    
    public boolean black = false;
    public boolean blinkMenu = false;
    public int timeBlink = 250;
    
    public boolean validate = false;
    
    public JPanel cards;
    
    Tetris tetris;
    
    public Menu(Tetris t){
        
        tetris = t;
        
        ga = new MenuController();
        
        timer = new Timer(ga.DELAY, this);
        timer.start();
        
        this.setFocusable(true);
        addKeyListener(new Menu.TAdapter());
        
        this.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentShown( ComponentEvent e ) {
                Menu.this.requestFocusInWindow();
            }
        });
        
        initGraphics();
    }
    
    private void initGraphics(){
        loadImage();
        setDoubleBuffered(true);
    }
    
    private void loadImage() { 
        backgroundNoir = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/0bis.png")).getImage();
        backgroundInsertCoin = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/0.png")).getImage();
        menu0 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu00.png")).getImage();
        menu1 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu01.png")).getImage();
        menu2 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu02.png")).getImage();
        menu3 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu03.png")).getImage();
        menu4 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu04.png")).getImage();
        menu5 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu05.png")).getImage();
        menu11 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu11.png")).getImage();
        menu12 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu12.png")).getImage();
        menu13 = new ImageIcon(getClass().getResource("/ressources/images/Backgrounds/menu13.png")).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(tetris.scaleX, tetris.scaleY);
        
        if(black) g2d.drawImage(backgroundNoir, 0, 0, null); 
        else g2d.drawImage(backgroundInsertCoin, 0, 0, null);
        
        if(blinkMenu == false) {
            switch(ga.getChoix()){
                case 1 :    
                    g2d.drawImage(menu1, 0, 0, null);     
                break;
                    
                case 2 :    
                    g2d.drawImage(menu2, 0, 0, null);     
                break;
                                    
                case 3 :    
                    g2d.drawImage(menu3, 0, 0, null);     
                break;
                    
                case 4 :    
                    g2d.drawImage(menu4, 0, 0, null);     
                break;
                                    
                case 5 :    
                    g2d.drawImage(menu5, 0, 0, null);     
                break;
                    
                case 11 :
                    g2d.drawImage(menu11, 0, 0, null);
                break;
                    
                case 12 :
                    g2d.drawImage(menu12, 0, 0, null);
                break;
                                        
                case 13 :
                    g2d.drawImage(menu13, 0, 0, null);
                break;
                                    
                default : break;
            }
            
        }
        else g2d.drawImage(menu0, 0, 0, null);
    } 

    @Override
    public void actionPerformed(ActionEvent e) {  
        if(timeBlink <= 0){
            if(black) black = false;
            else black = true;
            timeBlink = 250;
        } else timeBlink-=ga.DELAY;
        
        Menu.this.requestFocusInWindow();
        
        repaint();
    }
    
    class TAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {

            int keycode = e.getKeyCode();

            switch (keycode) {                   
                case KeyEvent.VK_DOWN:
                    if(ga.getChoix()+1<11) ga.setChoix(ga.getChoix() + 1);
                    else ga.setChoix2(ga.getChoix() + 1);
                    Sound.MOVE.play();
                break;
                    
                case KeyEvent.VK_UP:
                    if(ga.getChoix()<11) ga.setChoix(ga.getChoix() - 1);
                    else ga.setChoix2(ga.getChoix() - 1);
                    Sound.MOVE.play();
                break;
                    
                case KeyEvent.VK_ENTER:
                    Sound.CHANGE_BLOCK.play();
                    switch(ga.getChoix()){
                        case 1 : 
                            ga.setChoix2(11);
                        break;
                            
                        case 2 : 
                            tetris.newTwoPlayer();
                        break;
                            
                        case 3 : 
                            tetris.newHelp();
                        break;
                            
                        case 4 : 
                            tetris.newCredits();
                        break;
                            
                        case 5 :
                            System.exit(0);
                        break;
                            
                        case 6 :
                            tetris.newSoloVsAI();
                        break;
                            
                        case 11 : 
                            tetris.newSolo();
                        break;
                            
                        case 12 : 
                            tetris.newSoloVsAI();
                        break;
                            
                        case 13:
                            ga.setChoix(1);
                        break;
                    }
                    
                break;
                    
                case KeyEvent.VK_ESCAPE :
                    Sound.CHANGE_BLOCK.play();
                    System.exit(0);
                break;
                    
                default: break;
            }
        }
    }
}
