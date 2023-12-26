package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Tile {
    
    public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(48, 48, 16, 16);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(0, 32, 16, 16);
    public static BufferedImage TILE_FLOWER = Game.spritesheet.getSprite(48, 64, 16, 16);
    
    public static BufferedImage TILE_GRAMA = Game.spritesheet.getSprite(32, 48, 16, 16);
    public static BufferedImage TILE_TERRA = Game.spritesheet.getSprite(48, 48, 16, 16);
    public static BufferedImage TILE_AR = Game.spritesheet.getSprite(32, 64, 16, 16);



    private BufferedImage sprite;
    private int x,y;

    public Tile(int x, int y, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g){
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);

    }
}