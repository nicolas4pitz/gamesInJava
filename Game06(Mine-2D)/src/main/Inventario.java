package main;

import java.awt.Color;
import java.awt.Graphics;

import world.Tile;

public class Inventario {
    
    

    public int inventoryBoxSize = 48;

    public String[] itens = {"grama", "terra", "neve", "areia", "", ""}; 

    private int initialPosition = ((Game.WIDTH * Game.SCALE) / 2) - ((itens.length*inventoryBoxSize) / 2);

    public void tick(){

    }

    public void render(Graphics g){
        for(int i = 0; i < itens.length; i++){
            g.setColor(Color.gray);
            g.fillRect(initialPosition+(i*inventoryBoxSize), Game.HEIGHT*Game.SCALE-inventoryBoxSize, inventoryBoxSize, inventoryBoxSize);
            g.setColor(Color.black);
            g.drawRect(initialPosition+(i*inventoryBoxSize), Game.HEIGHT*Game.SCALE-inventoryBoxSize, inventoryBoxSize, inventoryBoxSize);
            if(itens[i] == "grama"){
                
                g.drawImage(Tile.TILE_GRAMA,initialPosition+(i*inventoryBoxSize) + 10, Game.HEIGHT*Game.SCALE-inventoryBoxSize + 10, 32, 32, null);
            } else if(itens[i] == "terra"){
                g.drawImage(Tile.TILE_TERRA,initialPosition+(i*inventoryBoxSize) + 10, Game.HEIGHT*Game.SCALE-inventoryBoxSize + 10, 32, 32, null);
            }
        }
    }

}
