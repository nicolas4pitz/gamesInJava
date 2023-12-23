package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Astar;
import world.Vector2i;
import world.World;

public class Enemy extends Entity{

    private int gravity = 0;

    private boolean right = true, left = false;

    public double vida = 4;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        path = Astar.findPath(Game.world, new Vector2i(World.xInitial, World.yInitial), new Vector2i(World.xFinal, World.yFinal));
        
    }

    public void tick(){
        followPath(path);
        if(x >= Game.WIDTH){
            Game.vida-=Entity.rand.nextDouble();
            Game.entities.remove(this);
            return;
        }
        if(vida <= 0){
            Game.entities.remove(this);
            Game.dinheiro+=2;
            return;
        }
    }

    public void render(Graphics g){
        super.render(g);
        g.setColor(Color.red);
        g.fillRect((int)x, (int)(y-5), 16, 6);
        
        g.setColor(Color.green);
        g.fillRect((int)x, (int)(y-5), (int)((vida/16)*64), 6);
    }
    
}
