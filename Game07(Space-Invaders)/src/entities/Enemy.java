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


    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        
    }

    public void tick(){
        y+=speed;
        if(y >= Game.HEIGHT){
            Game.entities.remove(this);
            return;
        }
    }
    
}
