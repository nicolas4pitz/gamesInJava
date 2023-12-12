package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Game;
import world.Astar;
import world.Camera;
import world.Vector2i;
import world.World;

public class Enemy extends Entity{
    

    private double speed = 0.5;

    private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;

    private int life = 10;

    private boolean isDamaged = false;
    private int damageFrames = 10, damageCurrent = 0;

    private BufferedImage[] sprites;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, null);
        sprites = new BufferedImage[2];
        sprites[0] = Game.spritesheet.getSprite(32, 48, 16, 16);
        sprites[1] = Game.spritesheet.getSprite(16, 32, 16, 16);
    }
    
    public void tick(){
        maskx = 5;
        masky = 5;
        depth = 0;
		if(!isCollidingWithPlayer()) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = Astar.findPath(Game.world, start, end);
			}
		}else {
			if(new Random().nextInt(100) < 5) {
				Game.player.life-=Game.rand.nextInt(3);
				Game.player.isDamaged = true;
			}
		}
			if(new Random().nextInt(100) < 50)
				followPath(path);
			
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 10) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = Astar.findPath(Game.world, start, end);
				}
			}

        if(path == null || path.size() == 0){
            Vector2i start = new Vector2i((int)(x/16), (int)(y/16));
            Vector2i end = new Vector2i((int)(Game.player.x/16), (int)(Game.player.y/16));
            path = Astar.findPath(Game.world, start, end);
        }

        followPath(path);

        frames++;
        if (frames == maxFrames){
            frames = 0;
            index++;
            if(index > maxIndex){
                index = 0;
            }
        }

        collidingBullet();

        if(life <= 0){
            destroySelf();
            return;
        }
        
        if (isDamaged){
            this.damageCurrent++;
            if(this.damageCurrent == this.damageFrames){
                this.damageCurrent = 0;
                this.isDamaged = false;
            }
        }
    
    }

    public void destroySelf(){
        Game.enemies.remove(this);
        Game.entities.remove(this);
    }

    public void collidingBullet(){
        for(int i = 0; i < Game.bullets.size(); i++){
            Entity e = Game.bullets.get(i);
            if (Entity.isColliding(this, e)){
                isDamaged = true;
                life--;
                Game.bullets.remove(i);
                return;
            }
        }
    }

    public boolean isCollidingWithPlayer(){
        Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, mwidth, mheight);

        Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
        
        return enemyCurrent.intersects(player);
    }


    

    public void render(Graphics g){
        if(!isDamaged){
            g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        } else{
            g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }

        //g.setColor(Color.BLUE);
        //g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
    }

}
