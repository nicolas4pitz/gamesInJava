package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Tile {
    public boolean solid = false;


    private BufferedImage sprite;
    protected int x,y;

    public Tile(int x, int y, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g){
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);

    }
}