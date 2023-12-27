package main;

import java.awt.Color;
import java.awt.Graphics;

import world.Camera;
import world.FloorTile;
import world.Tile;
import world.WallTile;
import world.World;

public class Inventario {
    
    public int selected = 0;
    public boolean isPressed = false;

    public boolean isPlaceItem = false;

    public int mx, my;

    public int inventoryBoxSize = 45;

    public String[] itens = {"grama", "terra", "neve", "areia", "ar", ""}; 

    private int initialPosition = ((Game.WIDTH * Game.SCALE) / 2) - ((itens.length*inventoryBoxSize) / 2);

    public void tick(){
        if(isPressed){
            isPressed = false;
            if(mx >= initialPosition && mx < initialPosition + (inventoryBoxSize * itens.length)){
                if(my >= Game.HEIGHT*Game.SCALE-inventoryBoxSize - 1 && my < Game.HEIGHT*Game.SCALE-inventoryBoxSize - 1 + inventoryBoxSize){
                    selected = (int) ((mx - initialPosition) / inventoryBoxSize);
                }
            }
        }
        if(isPlaceItem){
            isPlaceItem = false;
            mx = (int)mx/3 + Camera.x;
            my = (int)my/3 + Camera.y;

            int tilex = mx/16;
            int tiley = my/16;
            if(World.tiles[tilex+tiley*World.WIDTH].solid == false){
                if(itens[selected] == "grama"){
                    World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_GRAMA);
                } else if(itens[selected] == "terra"){
                    World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_TERRA);
                } else if(itens[selected] == "ar"){
                    World.tiles[tilex+tiley*World.WIDTH] = new FloorTile(tilex*16, tiley*16, Tile.TILE_AR);
                } else if(itens[selected] == "neve"){
                    World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_NEVE);
                } else if(itens[selected] == "areia"){
                    World.tiles[tilex+tiley*World.WIDTH] = new WallTile(tilex*16, tiley*16, Tile.TILE_NEVE);
                }

                if(World.isFree(Game.player.getX(), Game.player.getY()) == false){
                    World.tiles[tilex+tiley*World.WIDTH] = new FloorTile(tilex*16, tiley*16, Tile.TILE_AR);
                }
            }
        }
    }

    public void render(Graphics g){
        for(int i = 0; i < itens.length; i++){
            g.setColor(Color.gray);
            g.fillRect(initialPosition+(i*inventoryBoxSize) + 1, Game.HEIGHT*Game.SCALE-inventoryBoxSize - 1, inventoryBoxSize, inventoryBoxSize);
            g.setColor(Color.black);
            g.drawRect(initialPosition+(i*inventoryBoxSize) + 1, Game.HEIGHT*Game.SCALE-inventoryBoxSize - 1, inventoryBoxSize, inventoryBoxSize);
            if(itens[i] == "grama"){
                
                g.drawImage(Tile.TILE_GRAMA,initialPosition+(i*inventoryBoxSize) + 7, Game.HEIGHT*Game.SCALE-inventoryBoxSize + 7, 32, 32, null);
            } else if(itens[i] == "terra"){
                g.drawImage(Tile.TILE_TERRA,initialPosition+(i*inventoryBoxSize) + 7, Game.HEIGHT*Game.SCALE-inventoryBoxSize + 7, 32, 32, null);
            } else if(itens[i] == "ar"){
                g.drawImage(Tile.TILE_AR,initialPosition+(i*inventoryBoxSize) + 7, Game.HEIGHT*Game.SCALE-inventoryBoxSize + 7, 32, 32, null);
            } else if(itens[i] == "neve"){
                g.drawImage(Tile.TILE_NEVE,initialPosition+(i*inventoryBoxSize) + 7, Game.HEIGHT*Game.SCALE-inventoryBoxSize + 7, 32, 32, null);
            } else if(itens[i] == "areia"){
                g.drawImage(Tile.TILE_AREIA,initialPosition+(i*inventoryBoxSize) + 7, Game.HEIGHT*Game.SCALE-inventoryBoxSize + 7, 32, 32, null);
            }

            if(selected == i){
                g.setColor(Color.red);
                g.drawRect(initialPosition+(i*inventoryBoxSize), Game.HEIGHT*Game.SCALE-inventoryBoxSize-1, inventoryBoxSize, inventoryBoxSize);
            }
        }
    }

}
