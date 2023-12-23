package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import main.Game;

public class UI {
    
    public static BufferedImage hearth = Game.spritesheet.getSprite(32, 48, 8, 8);

    public void render (Graphics g){
        for (int i = 0; i < (int)(Game.vida); i++){
            g.drawImage(hearth, 20 + (i*20), 30, 36, 36, null);
        }

        g.setFont(new Font("arial", Font.BOLD, 24));
        g.setColor(Color.WHITE);
        g.drawString("$"+Game.dinheiro, (Game.WIDTH * Game.SCALE) - 60, 24);
    }



}
