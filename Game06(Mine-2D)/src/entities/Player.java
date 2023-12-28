package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;


public class Player extends Entity{

    public boolean right, left;

    public int dir = 1;
    private int gravity = 2;

    public double life = 100;

    public boolean jump = false;
    public int jumpHeight = 58;
    public int jumpframe = 0;
    public boolean isJumping = false;

    private int framesAnimation = 0;
    private int maxFrames = 15;

    private int maxSprite = 4;
    private int curSprite = 0;

    public BufferedImage attack_Right;
    public BufferedImage attack_Left;

    public boolean attack = false;
    public boolean isAttackig = false;
    public int attackFrames = 0;
    public int maxFramesAttack = 20;

    public int lastDir = 0;


    public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);

        attack_Right = Game.spritesheet.getSprite(64, 32, 16, 16);
        attack_Left = Game.spritesheet.getSprite(80, 32, 16, 16);
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
            jumpHeight = 58;
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
        
        //Sistema de Ataque
        if(attack){
            if(isAttackig == false){
                attack = false;
                isAttackig = true;
            }
        }

        if(isAttackig){
            attackFrames++;
            if(attackFrames == this.maxFramesAttack){
                attackFrames = 0;
                isAttackig = false;
            }
        }

        collisionEnemy();

        Camera.x = Camera.clamp((int)x-Game.WIDTH/2, 0, World.WIDTH*16 - Game.WIDTH);
        Camera.y = Camera.clamp((int)y-Game.HEIGHT/2, 0, World.HEIGHT*16 - Game.HEIGHT);	
    }

    public void collisionEnemy(){
        for(int i = 0; i < Game.entities.size(); i++){
            Entity e = Game.entities.get(i);
            if(e instanceof Enemy){
                if(Entity.rand.nextInt(100) < 30){
                    if(Entity.isColliding(this, e)){
                        life-=0.3;
                        if(isAttackig){
                            ((Enemy) e).vida--;
                        }
                    }
                    
                }
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
            lastDir = 1;
            sprite = Entity.Player_Sprite_RIGHT[curSprite];
            if(isAttackig){
                g.drawImage(attack_Right, this.getX()+8-Camera.x, this.getY() - Camera.y, null);
            }
        } else if(left){
            lastDir = 2;
            if(isAttackig){
                g.drawImage(attack_Left, this.getX()-8-Camera.x, this.getY() - Camera.y, null);
            }
            sprite = Entity.Player_Sprite_LEFT[curSprite];
        } else{
            sprite = Entity.Player_Sprite;
            if(lastDir == 1 && isAttackig){
                g.drawImage(attack_Right, this.getX()+8-Camera.x, this.getY() - Camera.y, null);
            } else if(lastDir == 2 && isAttackig){
                g.drawImage(attack_Left, this.getX()-8-Camera.x, this.getY() - Camera.y, null);
            }
        }

        super.render(g);
    }
}
