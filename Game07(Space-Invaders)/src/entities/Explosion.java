package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Explosion extends Entity{

    private int frames = 0;
    private int targetFrames = 5;
    private int maxAnimation = 4;
    private int curAnimation = 0;

    public BufferedImage[] explosionSprite = new BufferedImage[5];

    public Explosion(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        //TODO Auto-generated constructor stub
        explosionSprite[0] = Game.spritesheet.getSprite(0, 32, 16, 16);
        explosionSprite[1] = Game.spritesheet.getSprite(16, 32, 16, 16);
        explosionSprite[2] = Game.spritesheet.getSprite(32, 32, 16, 16);
        explosionSprite[3] = Game.spritesheet.getSprite(48, 32, 16, 16);
        explosionSprite[4] = Game.spritesheet.getSprite(64, 32, 16, 16);

    }

    public void tick(){
        frames++;
        if(frames == targetFrames){
            frames = 0;
            curAnimation++;
            if(curAnimation > maxAnimation){
                Game.entities.remove(this);
                return;
            }
        }
    }

    public void render(Graphics g){
        g.drawImage(explosionSprite[curAnimation], this.getX(), this.getY(), null);
    }
    
}
