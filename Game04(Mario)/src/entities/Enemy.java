package entities;

import java.awt.image.BufferedImage;

import world.World;

public class Enemy extends Entity{

    private int gravity = 0;

    private boolean right = true, left = false;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);

    }

    public void tick(){
        if(World.isFree((int)x, (int)(y+gravity))) {
            y += gravity;
        }

        if(right && World.isFree((int)(x+speed), (int)y)){
            x+=speed;
            if(World.isFree((int)(x+16), (int)y+1)){
                right = false;
                left = true;
            }
        } else if(left && World.isFree((int)(x-speed), (int)y)){
            x-=speed;
            if(World.isFree((int)x-16, (int)y+1)){
                right = true;
                left = false;
            }
        }
    }
    
}
