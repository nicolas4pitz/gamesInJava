package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Game;
import world.World;


public class Player extends Entity{

    public boolean right, left;

    public int dir = 1;
    private int gravity = 2;

    public boolean jump = false;
    public int jumpHeight = 48;
    public int jumpframe = 0;
    public boolean isJumping = false;

    private int framesAnimation = 0;
    private int maxFrames = 15;

    private int maxSprite = 4;
    private int curSprite = 0;


    public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    public void tick(){
        depth = 2;
        if(World.isFree((int)x, (int)(y+gravity)) && isJumping == false) {
            y += gravity;
        }

        if(right && World.isFree((int)(x+speed), (int)y)){
            x+=speed;
            dir = 1;
        } else if(left && World.isFree((int)(x-speed), (int)y)){
            x-=speed;
            dir = -1;
        }

        if(jump) {
            if(!World.isFree(this.getX(), this.getY()+1)){
                isJumping = true;
            } else{
                jump = false;
            }
        }

        if(isJumping){
            if(World.isFree(this.getX(), this.getY()-2)){
                y-=2;
                jumpframe+=2;
                if(jumpframe == jumpHeight){
                    isJumping = false;
                    jump = false;
                    jumpframe = 0;
                }
            } else{
                isJumping = false;
                jump = false;
                jumpframe = 0;
            }
        }
    }

    public void render(Graphics g){
        framesAnimation++;
        if(framesAnimation == maxFrames){
            curSprite++;
            framesAnimation = 0;
            if(curSprite == maxSprite){
                curSprite = 0;
            }
        }
        if(right){
            sprite = Entity.Player_Sprite_RIGHT[curSprite];
        } else if(left){
            sprite = Entity.Player_Sprite_LEFT[curSprite];
        } else{
            sprite = Entity.Player_Sprite;
        }

        super.render(g);
    }
}
