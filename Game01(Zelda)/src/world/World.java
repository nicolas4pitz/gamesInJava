package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Bullet;
import entities.Enemy;
import entities.Entity;
import entities.Flower;
import entities.Lifepack;
import entities.Player;
import entities.Weapon;
import graficos.Spritesheet;
import main.Game;

public class World {
    
    private BufferedImage map;

    public static Tile[] tiles;
    public static int WIDTH, HEIGHT;

    public static final int TILE_SIZE = 16;

    public World(String path){
        Game.player.setX(0);
        Game.player.setY(0);
        WIDTH = 100;
        HEIGHT = 100;
        tiles = new Tile[WIDTH*HEIGHT];
        
        for(int xx = 0; xx < WIDTH; xx++){
            for(int yy = 0; yy < HEIGHT; yy++){
                tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
            }
        }
        
        int dir = 0;
        int xx = 0, yy = 0;

        for(int i = 0; i < 200; i++){
            tiles[xx+yy*WIDTH] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
            if(dir == 0){
                //direita
                if(xx < WIDTH){
                    xx++;
                }
            } else if(dir == 1){
                //esquerda
                if(xx > 0){
                    xx--;
                }
            } else if(dir == 2){
                //baixo
                if(yy < HEIGHT){
                    yy++;
                }
            } else if(dir == 3){
                //cima
                if(yy > 0){
                    yy--;
                }
            }

            if(Game.rand.nextInt(100) < 30){
                dir = Game.rand.nextInt(4);
            }
        }
    }

    public static boolean isFree(int xnext, int ynext){
        int x1 = xnext / TILE_SIZE;
        int y1 = ynext / TILE_SIZE;

        int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
        int y2 = ynext / TILE_SIZE;
    
        int x3 = xnext / TILE_SIZE;
        int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;

        int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
        int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;

        return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
    }

    public static void restartGame(String level){
        Game.entities.clear();
        Game.enemies.clear();
        Game.bullets.clear();
        Game.entities = new ArrayList<Entity>();
        Game.enemies = new ArrayList<Enemy>();
        Game.spritesheet = new Spritesheet("spritesheet.png");
        Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(0, 0, 16, 16));
        Game.entities.add(Game.player);
        Game.world = new World(""+level);
        return;
    }

    public void render(Graphics g){
        int xstart = Camera.x >> 4;
        int ystart = Camera.y >> 4;

        int xfinal = xstart + (Game.WIDTH >> 4);
        int yfinal = ystart + (Game.HEIGHT >> 4);
        for (int xx = xstart; xx <= xfinal; xx++){
            for (int yy = ystart; yy <= yfinal; yy++){
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT){
                    continue;
                }
                Tile tile = tiles[xx + (yy*WIDTH)];
                tile.render(g);
            }
        }
    }    

    /*public static void renderMinimap(){
        for(int i = 0; i < Game.minimapaPixels.length; i++){
            Game.minimapaPixels[i] = 0;
        }
        for(int xx = 0; xx < WIDTH; xx++){
            for(int yy = 0; yy < HEIGHT; yy++){
                if(tiles[xx + (yy*WIDTH)] instanceof WallTile){
                    Game.minimapaPixels[xx + (yy*WIDTH)] = 0xff0000;
                }
            }
        }
        int xPlayer = Game.player.getX()/16;
        int yPlayer = Game.player.getY()/16;

        Game.minimapaPixels[xPlayer + (yPlayer*WIDTH)] = 0x0000ff;
    }*/

}

