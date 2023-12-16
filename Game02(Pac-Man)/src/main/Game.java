package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import entities.Entity;
import entities.Player;
import graficos.Spritesheet;
import graficos.UI;
import world.World;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{

    public static JFrame frame;
    private Thread thread;
    private boolean isRunning;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    private BufferedImage image;

    public static List<Entity> entities;

    public static Spritesheet spritesheet;
    public static Player player;
    public static World world;

    public UI ui;

    public static int frutas_atual = 0;
    public static int frutas_contagem = 0;

    public int xx, yy;
    
    public static int entrada = 1;
    public static int comecar = 2;
    public static int jogando = 3;
    public static int estado_cena = entrada;
    public int timeCena = 0, maxTimeCena = 60*3;

    //public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelart.ttf");
    //public Font newFont;

    public boolean saveGame = false;

    /*public static BufferedImage minimapa;
    public static int[] minimapaPixels;*/

    public Game() {
        //Sound.musicBackground.loop();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        spritesheet = new Spritesheet("spritesheet.png");
        entities = new ArrayList<Entity>();
        player = new Player(0, 0, 16,  16, 2, spritesheet.getSprite(0, 0, 16, 16));
        world = new World("level1.png");
        ui = new UI();
        
        
        
        entities.add(player);
    }

    public void initFrame() {
        frame = new JFrame("Pac-Man");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        requestFocus();
    }

    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop(){
        isRunning = false;
        try{
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        Game game = new Game();
        game.start();
        
    }

    public void tick(){
        for (int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.tick();
        }
    }


    public void render(){
        // Obtenha a estratégia de buffer para o canvas
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            // Crie uma nova estratégia de buffer com 3 buffers se não houver uma estratégia existente
            this.createBufferStrategy(3);
            return;
        }
        // Obtenha o objeto gráfico para a estratégia de buffer
        Graphics g = image.getGraphics();
        // Defina a cor de fundo como preto
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        world.render(g);
        // Sort the entities list by Z-index
        Collections.sort(entities, Entity.nodeSorter);
        // Loop through each entity in the entities list
        for (int i = 0; i < entities.size(); i++){
            // Get the entity
            Entity e = entities.get(i);
            // Render the entity
            e.render(g);
        }
        //applyLight();
        

        g.dispose();

        // Get the world graphics object
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        ui.render(g);

        bs.show();
    
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;    
        double timer = System.currentTimeMillis();
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS" + frames);
                frames = 0;
                timer += 1000;
            }
        }

        stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
            player.right = true;
        }  else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
            player.up = true;

        } else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
            player.right = false;
        }  else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
            player.up = false;
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
            player.down = false;
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

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
}
