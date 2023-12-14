package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, MouseListener, MouseMotionListener{

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public BufferedImage sprite1;
    public BufferedImage sprite2;

    public int x1 = 30, y1 = 90;
    public int x2 = 100, y2 = 100;

    public int[] pixels1;
    public int[] pixels2;

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addMouseListener(this);

        try {
            sprite1 = ImageIO.read(getClass().getResource(("sprite1.png")));
            sprite2 = ImageIO.read(getClass().getResource(("sprite2.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        pixels1 = new int[sprite1.getWidth()*sprite1.getHeight()];
        sprite1.getRGB(0, 0, sprite1.getWidth(), sprite1.getHeight(), pixels1, 0, sprite1.getWidth());
        
        pixels2 = new int[sprite2.getWidth()*sprite2.getHeight()];
        sprite2.getRGB(0, 0, sprite2.getWidth(), sprite2.getHeight(), pixels2, 0, sprite2.getWidth());

    }

    public void update(){
        x1++;
        if(this.isColliding(x1, y1, x2, y2, pixels1, pixels2, sprite1, sprite2)){
            System.out.println("Estão Colidindo");
        }
    }

    public static void main(String[] args){
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setTitle("Pixel Perfect");
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        new Thread(game).start();
    }


    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(sprite1, x1, y1, null);
        g.drawImage(sprite2, x2, y2, null);

        g.dispose();
        
        bs.show();
    
    }

    public boolean isColliding(int x1, int y1, int x2, int y2, int[] pixels1, int[] pixels2, BufferedImage sprite1, BufferedImage sprite2){
        for(int xx1 = 0; xx1 < sprite1.getWidth(); xx1++){
            for(int yy1 = 0; yy1 < sprite1.getHeight(); yy1++){
                for(int xx2 = 0; xx2 < sprite2.getWidth(); xx2++){
                    for(int yy2 = 0; yy2 < sprite2.getHeight(); yy2++){
                        int pixelAtual1 = pixels1[xx1+yy1*sprite1.getWidth()];
                        int pixelAtual2 = pixels2[xx2+yy2*sprite2.getWidth()];
                        if(pixelAtual1 == 0x000000 || pixelAtual2 == 0x000000){
                            continue;
                        }
                        if(xx1+x1 == xx2+x2 & yy1+y1 == yy2+y2){
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    @Override
    public void run() {
        double fps = 5.0;
        while (true) {
            update();
            render();
            try{
                Thread.sleep((int)(1000/fps));
            } catch (InterruptedException e){

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    
}
