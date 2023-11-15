package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.Player;

public class UI {
    
    public void render (Graphics g){
        g.setColor(Color.RED);
        g.fillRect(8, 4, 50, 8);
        g.setColor(Color.GREEN);
        g.fillRect(8, 4, (int)((Player.life/Player.maxlife)*50), 8);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 8));
        g.drawString((int)Player.life+"/"+(int)Player.maxlife, 20, 11);
    }

}
