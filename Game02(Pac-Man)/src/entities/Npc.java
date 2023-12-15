package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Npc extends Entity{

    public String[] frases = new String[5];

    public boolean showMessage = false;
    public boolean show = false;

    public Npc(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        frases[0] = ("Olá! Seja muito bem-vindo ao Mundo");
    }
    
    public void tick(){
        int xPlayer = Game.player.getX();
        int yPlayer = Game.player.getY();
    
        int xNpc = (int)x;
        int yNpc = (int)y;
    
    
        if(Math.abs(xPlayer-xNpc) < 30 && Math.abs(yPlayer-yNpc) < 30){
            if(show == false){
                showMessage = true;
                show = true;
            }
        } else{
            showMessage = false;
        }
    }

    public void render(Graphics g){
        super.render(g);
        if(showMessage){
            g.setColor(Color.white);
            g.fillRect(9, 9, Game.WIDTH - 18, Game.HEIGHT - 18);
            g.setColor(Color.blue);
            g.fillRect(10, 10, Game.WIDTH - 20, Game.HEIGHT - 20);

            g.setFont(new Font("Arial", Font.BOLD, 9));
            g.setColor(Color.black);
            g.drawString(frases[0], (int)x, (int)y);

            g.drawString(">Pressione Enter para fechar<", (int)x+10, (int)y+10);
        }
    }
}
