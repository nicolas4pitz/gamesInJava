package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Spawner extends Entity {

    private int timer = 60;
    private int cur_timer = 0;

    public Spawner(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        //TODO Auto-generated constructor stub
    }
    

    public void tick() {
        // Criar Inimigos
        cur_timer++;
        if(cur_timer == timer){
            Enemy enemy = new Enemy(x, y, 16, 16, Entity.rand.nextGaussian() , Entity.Enemy);
            Game.entities.add(enemy);
            timer = Entity.rand.nextInt(60 - 30) + 30;
            cur_timer=0;
        }
    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, width, height);
    }


}
