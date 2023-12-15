package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Game;
import world.Camera;

public class Particle extends Entity{

    public int lifeTime = 15;
    public int curLife = 0;

    public int spd = 4;
    public double dx = 4;
    public double dy = 4;

    public Particle(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        //TODO Auto-generated constructor stub

        dx = new Random().nextGaussian();
        dy = new Random().nextGaussian();
    }

    public void tick(){
        x+=dx*spd;
        y+=dy*spd;
        curLife++;
        if(lifeTime == curLife){
            Game.entities.remove(this);
        }
    }
    
    public void render(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
    }

}
