package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{

    public boolean right,up,left,down;

    public BufferedImage sprite_Left;

    public int lastDir = 1;

    public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        sprite_Left = Game.spritesheet.getSprite(16, 0, 16, 16);
    }

    public void tick(){
        depth = 1;
        if(right && World.isFree((int)(x + speed), this.getY())){
            x+=speed;
            lastDir = 1;
        } else if (left && World.isFree((int)(x - speed), this.getY())){
            lastDir = -1;
            x-=speed;
        }

        if(up && World.isFree(this.getX(), (int)(y - speed))){
            y-=speed;
        } else if(down && World.isFree(this.getX(), (int)(y + speed))){
            y+=speed;
        }

        verificarPegaFruta();

        if(Game.frutas_contagem == Game.frutas_atual){
            System.out.println("Ganhamos o Jogo");
            World.restartGame();
        }
    }

    public void verificarPegaFruta(){
        for (int i = 0; i  < Game.entities.size(); i++){
            Entity current = Game.entities.get(i);
            if(current instanceof Fruta){
                if(Entity.isColliding(this, current)){
                    Game.frutas_atual++;
                    Game.entities.remove(i);
                    return;
                }
            }
        }
    }

    public void render(Graphics g){
        if(lastDir == 1){
            super.render(g);
        } else {
            g.drawImage(sprite_Left, this.getX() - Camera.x, this.getY() - Camera.y , null);
        }
    }
    
}
