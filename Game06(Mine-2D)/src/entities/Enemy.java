package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.FloorTile;
import world.Tile;
import world.WallTile;
import world.World;

public class Enemy extends Entity{

    private int gravity = 1;

    private boolean right = true, left = false;

    public double vida = Entity.rand.nextInt(100-20) + 30;
    public double maxLife = vida;

    public int dir = 1;

    public BufferedImage sprite1, sprite2;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite1, BufferedImage sprite2) {
        super(x, y, width, height, speed, null);
        this.sprite1 = sprite1;
        this.sprite2 = sprite2;

    }

    public void tick(){
        if(World.isFree((int)x, (int)(y+gravity))) {
            y += gravity;
        }

        if(dir == 1){
            if(World.isFree((int)(x+speed), (int)(y))){
                x+=speed;
            }else{
                int xnext = (int)((x+speed) / 16) + 1;
                int ynext = (int)(y / 16);
                if(World.tiles[xnext+ynext * World.WIDTH] instanceof WallTile && World.tiles[xnext+ynext/World.WIDTH].solid == false){
                    World.tiles[xnext+ynext*World.WIDTH] = new FloorTile(xnext*16, ynext*16, Tile.TILE_AR);
                }
                dir=-1;
                left = true;
                right = false;
            } 
        } else if(dir==-1){
            if(World.isFree((int)(x-speed), (int)(y))){
                x-=speed;
            } else{
                int xnext = (int)((x-speed) / 16);
                int ynext = (int)(y / 16);
                if(World.tiles[xnext+ynext * World.WIDTH] instanceof WallTile && World.tiles[xnext+ynext/World.WIDTH].solid == false){
                    World.tiles[xnext+ynext*World.WIDTH] = new FloorTile(xnext*16, ynext*16, Tile.TILE_AR);
                }
                dir=1;
                right = true;
                left = false;
            }
        }

        if(vida == 0){
            Game.entities.remove(this);
            return;
        }
        
    }

    public void render(Graphics g){
        if(right){
            sprite = sprite1;
        } else if(left) {
            sprite = sprite2;
        }

        super.render(g);

        int curLife = (int)((vida/maxLife) * 20);
        g.setColor(Color.RED);
        g.fillRect(this.getX()-2-Camera.x, this.getY() - 7 - Camera.y, 20, 7);
        g.setColor(Color.GREEN);
        g.fillRect(this.getX()-2-Camera.x, this.getY() - 7 - Camera.y, curLife, 7);
    }
    
}
