package entities;

import java.awt.image.BufferedImage;

import main.Game;
import world.Astar;
import world.Vector2i;
import world.World;

public class Enemy extends Entity{

    private int gravity = 0;

    private boolean right = true, left = false;

    public int vida = 4;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        path = Astar.findPath(Game.world, new Vector2i(World.xInitial, World.yInitial), new Vector2i(World.xFinal, World.yFinal));
        
    }

    public void tick(){
        followPath(path);
        if(x >= Game.WIDTH){
            System.out.println("Perdemos Vida");
            Game.entities.remove(this);
            return;
        }
    }
    
}
