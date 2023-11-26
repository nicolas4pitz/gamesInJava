package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graficos.Spritesheet;
import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{

    public boolean right,up,left,down;
    public int right_dir = 0, left_dir = 1;
    public int dir = right_dir;
    public double speed = 1.6;

    
    private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
    private boolean moved = false;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;

    private BufferedImage playerDamage;
    private BufferedImage playerDamageRight;
    private BufferedImage playerDamageLeft;

    public int ammo = 0;

    public boolean isDamaged = false;
    private int damageFrame = 0;

    public double life = 100, maxlife = 100;

    private boolean hasGun = false;

    public boolean shoot = false, mouseShoot = false;
    public int mx, my;

    public boolean jump = false;

    public boolean isJumping = false;

    public boolean jumpUp = false, jumpDown = false;

    public int jumpSpeed = 2;

    public int z = 0;

    public int jumpFrames = 50, jumpCur = 0;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        playerDamage = Game.spritesheet.getSprite(32, 32, 16, 16);
        playerDamageRight = Game.spritesheet.getSprite(16, 64, 16, 16);
        playerDamageLeft = Game.spritesheet.getSprite(16, 80, 16, 16);
        for (int i = 0; i < 4; i++) {
            rightPlayer[i] = Game.spritesheet.getSprite(0 + (i * 16), 0, 16, 16);
        }
        for (int i = 0; i < 4; i++) {
            leftPlayer[i] = Game.spritesheet.getSprite(0 + (i * 16), 16, 16, 16);  
        }
        
    }

    public void tick(){
        depth = 1;
        if(jump){
            if(isJumping == false){
                jump = false;
                isJumping = true;
                jumpUp = true;
            }
        }

        if(isJumping == true){
            if(jumpUp){
                jumpCur+=jumpSpeed;
            } else if(jumpDown){
                jumpCur-=jumpSpeed;
                if(jumpCur <= 0){
                    isJumping = false;
                    jumpDown = false;
                    jumpUp = false;
                }
            }
            z = jumpCur;
            if(jumpCur >= jumpFrames){
                jumpUp = false;
                jumpDown = true;
                //System.out.println("Chegou na altura maxima");
            }
        }

        moved = false;
        if(right && World.isFree((int)(x + speed), this.getY())){
            moved = true;
            dir = right_dir;
            x+=speed;
        } else if (left && World.isFree((int)(x - speed), this.getY())){
            moved = true;
            dir = left_dir;
            x-=speed;
        }

        if(up && World.isFree(this.getX(), (int)(y - speed))){
            moved = true;
            y-=speed;
        } else if(down && World.isFree(this.getX(), (int)(y + speed))){
            moved = true;
            y+=speed;
        }

        if (moved){
            frames++;
            if (frames == maxFrames){
                frames = 0;
                index++;
                if(index > maxIndex){
                    index = 0;
                }
            }
        }

        this.checkCollisonLifePack();
        this.checkCollisionAmmo();
        this.checkCollisionGun();

        if(isDamaged){
            this.damageFrame++;
            if(this.damageFrame == 8){
                this.damageFrame = 0;
                isDamaged = false;
            }
        }

        if (shoot && hasGun && ammo > 0){
            /*if(arma && ammo > 0){
                Tudo aqui dentro case dê merda no futuro
            }*/
            ammo--;
            //Criar bala e atirar
            shoot = false;
            int dx = 0;
            int px = 0;
            int py = 6;
            if(dir==right_dir){
                px = 18;
                dx = 1;
            } else{
                px = -8;
                dx = -1;
            }

            BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, 3, 3, null, dx, 0);
            Game.bullets.add(bullet);
        }

        if(mouseShoot && hasGun && ammo > 0){
            mouseShoot = false;
            //Criar bala e atirar
            
            ammo--;    
            
            int px = 0, py = 8;
            double angle = 0;
            if(dir==right_dir){
                px = 18;
                angle = Math.atan2(my - (this.getY()+py - Camera.y), mx - (this.getX()+px - Camera.x));
            } else{
                px = -8;
                angle = Math.atan2(my - (this.getY()+py - Camera.y), mx - (this.getX()+px - Camera.x));
            }

            double dx = Math.cos(angle);
            double dy = Math.sin(angle);

            BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, 3, 3, null, dx, dy);
            Game.bullets.add(bullet);
        }


        if(life <= 0){
            //Game over
            life = 0;
            Game.gameState = "GAME_OVER";
        }

        uptadeCamera();
    }

    public void uptadeCamera(){
        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
    }

    public void checkCollisionGun(){
        for(int i = 0; i < Game.entities.size(); i++){
            Entity atual = Game.entities.get(i);
            if (atual instanceof Weapon){
                if(Entity.isColliding(this, atual)){
                    hasGun = true;
                    //System.out.println("Pegou a arma");
                    Game.entities.remove(atual);
                }
            }
        }
    }


    public void checkCollisionAmmo(){
        for(int i = 0; i < Game.entities.size(); i++){
            Entity atual = Game.entities.get(i);
            if (atual instanceof Bullet){
                if(Entity.isColliding(this, atual)){
                    ammo+=100;
                    Game.entities.remove(atual);
                }
            }
        }
    }

    public void checkCollisonLifePack(){
        for(int i = 0; i < Game.entities.size(); i++){
            Entity atual = Game.entities.get(i);
            if (atual instanceof Lifepack){
                if(Entity.isColliding(this, atual)){
                    life += 10;
                    if (life > 100) {
                        life = 100;
                    }
                    Game.entities.remove(atual);
                }
            }
        }
    }


    public void render (Graphics g){
        if(!isDamaged){
            if(dir == right_dir){
                g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
                if(hasGun){
                    //Desenhar Para direita
                    g.drawImage(Entity.GUN_RIGHT, this.getX()+8 - Camera.x, this.getY() - Camera.y - z, null);
                }
            }else if(dir == left_dir) {
                g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
                if (hasGun){
                    //Desenhar parar esquerda
                    g.drawImage(Entity.GUN_LEFT, this.getX()-8 - Camera.x, this.getY() - Camera.y - z, null);
                }
            }
        } else {
            if(dir == right_dir && hasGun){
                g.drawImage(playerDamageRight, this.getX()+8 - Camera.x, this.getY() - Camera.y - z, null);
                g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y - z , null);
            } else if(dir == left_dir && hasGun) {
                g.drawImage(playerDamageLeft, this.getX()-8 - Camera.x, this.getY() - Camera.y - z, null);
                g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
            } else{
                g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y -z , null);
            }
            
        }
        if(isJumping){
            g.setColor(Color.BLACK);
            g.fillOval(this.getX()-Camera.x+4, this.getY() - Camera.y + 12, 8, 8);
        }
    }
    
}
