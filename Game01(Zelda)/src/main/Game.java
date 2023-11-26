package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entities.BulletShoot;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import graficos.Spritesheet;
import graficos.UI;
import world.World;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{

    public static JFrame frame;
    private Thread thread;
    private boolean isRunning;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;

    private int CUR_LEVEL = 1, MAX_LEVEL = 2;
    private BufferedImage image;

    public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static List<BulletShoot> bullets;
    public static Spritesheet spritesheet;

    public static Player player;

    public static World world;

    public static Random rand;

    public UI ui;

    public Menu menu;

    public int xx, yy;

    //public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelart.ttf");
    //public Font newFont;

    public boolean saveGame = false;

    public static String gameState = "MENU";
    private boolean showMessageGameOver = true;
    private int framesGameOver = 0;
    private boolean restartGame = false;

    public int mx, my;

    public int[] pixels;
    public BufferedImage lightMap;
    public int[] lightmappixels;

    public Game() {
        //Sound.musicBackground.loop();
        rand = new Random();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        initFrame();
        
        ui = new UI();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        try {
            lightMap = ImageIO.read(getClass().getResource("lightmap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lightmappixels = new int[lightMap.getWidth() * lightMap.getHeight()];
        lightMap.getRGB(0, 0, lightMap.getWidth(), lightMap.getHeight(), lightmappixels, 0, lightMap.getWidth());
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        entities = new ArrayList<Entity>();
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<BulletShoot>();

        spritesheet = new Spritesheet("spritesheet.png");
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(0, 0, 16, 16));
        entities.add(player);
        world = new World("level1.png");
        /* 
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(56f);
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        menu = new Menu();
    }

    public void initFrame() {
        frame = new JFrame("ZELDA");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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
        if(gameState == "Normal"){
            if(this.saveGame){
                this.saveGame = false;
                String[] opt1 = {"level", "vida"};
                int[] opt2 = {this.CUR_LEVEL, (int)player.life};
                Menu.saveGame(opt1, opt2, 10);
                System.out.println("Jogo Salvo");
            }
            this.restartGame = false;
            for (int i = 0; i < entities.size(); i++){
                Entity e = entities.get(i);
                e.tick();
            }
            for(int i = 0; i < bullets.size(); i++){
                bullets.get(i).tick();
            }

            if(enemies.size() == 0){
                //Avançar para o proximo level
                CUR_LEVEL++;
                if(CUR_LEVEL > MAX_LEVEL){
                    CUR_LEVEL = 1;
                }
                String newWorld = "level"+CUR_LEVEL+".png";
                World.restartGame(newWorld);
            }
        } else if(gameState == "GAME_OVER"){
            
            this.framesGameOver++;
            if(this.framesGameOver == 30){
                this.framesGameOver = 0;
                if(this.showMessageGameOver){
                    this.showMessageGameOver = false;
                } else {
                    this.showMessageGameOver = true;
                }
            }
            if(this.restartGame){
                this.restartGame = false;
                this.gameState = "Normal";
                CUR_LEVEL = 1;
                String newWorld = "level"+CUR_LEVEL+".png";
                World.restartGame(newWorld);
            }
        } else if(gameState == "MENU"){
            player.uptadeCamera();
            menu.tick();
        }

        requestFocus();
    }

    /*public void drawRectangleExample(int xOff, int yOff){
        for(int xx = 0; xx < 32; xx++){
            for(int yy = 0; yy < 32; yy++){
                int xoff = xx + xOff;
                int yoff = yy + yOff;
                if(xoff < 0 || yoff < 0 || xoff >= WIDTH || yoff >= HEIGHT){
                    continue;
                }
                pixels[xoff + (yoff * WIDTH)] = 0xFF0000;
            }
        }
    }*/

    /*public void applyLight(){
        for(int xx = 0; xx < Game.WIDTH; xx++){
            for(int yy = 0; yy < Game.HEIGHT; yy++){
                if(lightmappixels[xx + (yy*Game.WIDTH)] == 0x000000){
                    
                    pixels[xx +(yy*Game.WIDTH)] = 0;
                }
            }
        }
    }*/

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        world.render(g);
        Collections.sort(entities, Entity.nodeSorter);
        for (int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.render(g);
        }
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).render(g);;
        }
        //applyLight();
        ui.render(g);

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        g.setFont(new Font("arial", Font.BOLD, 17));
        g.setColor(new Color(255,255,255));
        g.drawString("Munição: "+ player.ammo, 600, 24);
        if(gameState == "GAME_OVER"){
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
            g.setFont(new Font("arial", Font.BOLD, 28));
            g.setColor(new Color(255,255,255));
            g.drawString("GAME OVER", (WIDTH*SCALE) / 2 - 90, (HEIGHT*SCALE) / 2 - 20);
            g.setFont(new Font("arial", Font.BOLD, 32));
            if(showMessageGameOver){
                 g.drawString(">Pressione Enter para Reiniciar<", (WIDTH*SCALE) / 2 - 260, (HEIGHT*SCALE) / 2 + 40);
            }
        } else if(gameState == "MENU"){
            menu.render(g);
        }
        /*
        Renderizar Quadrado que gira a medida que o mouse se movimenta        
        Graphics2D g2 = (Graphics2D) g;
        double angleMouse = Math.atan2(my - 200+25, mx - 200+25);
        
        g2.rotate(angleMouse, 200+25, 200+25);
        g.setColor(Color.red);
        g.fillRect(200, 200, 50, 50);*/
        /* 
        g.setFont(newFont);
        g.setColor(Color.red);
        g.drawString("Teste COm nova font", 20, 20);
        */
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
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_Z){
            player.jump = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
            player.right = true;
        }  else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
            player.up = true;

            if(gameState == "MENU"){
                menu.up = true;
            }
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
            player.down = true;

            if(gameState == "MENU"){
                menu.down = true;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            player.shoot = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            this.restartGame = true;
            if(gameState == "MENU"){
                menu.enter = true;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gameState = "MENU";
            menu.pause = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_G){
            if(gameState == "Normal"){
                this.saveGame = true;
            }
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

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            player.shoot = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        player.mouseShoot = true;
        player.mx = (e.getX() / 3);
        player.my = (e.getY() / 3);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        player.mouseShoot = false;
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
        this.mx = e.getX();
        this.my = e.getY();
    }
    
}
