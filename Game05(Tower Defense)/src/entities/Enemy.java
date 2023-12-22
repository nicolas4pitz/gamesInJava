package entities;

import java.awt.image.BufferedImage;

import world.World;

public class Enemy extends Entity{

    private int gravity = 0;

    

    private boolean right = true, left = false;

    public int vida = 4;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);

    }

    public void tick(){
        x++;
    }
    
}
