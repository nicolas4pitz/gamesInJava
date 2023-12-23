package entities;

import java.awt.image.BufferedImage;

import main.Game;
import world.World;

public class TowerControler extends Entity{

    public boolean ispressed = false;
    public int xTarget, yTarget;

    public TowerControler(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        //TODO Auto-generated constructor stub
    }

    public void tick(){
        if(ispressed){
            ispressed = false;
            boolean liberado = true;
            //Criar torre no mapa
            int xx = (xTarget/16) * 16;
            int yy = (yTarget/16) * 16;
            Player player = new Player(xx, yy, 16, 16, 0, Game.spritesheet.getSprite(16, 48, 16, 16));
            for(int i = 0; i < Game.entities.size(); i++){
                Entity e = Game.entities.get(i);
                if(e instanceof Player){
                    if(Entity.isColliding(e, player)){
                        liberado = false;
                        System.out.println("Ja existe torre");
                    } 
                }
            }

            if(World.isFree(xx, yy)){
                liberado = false;
                System.out.println("Posicao ocupada");
            }

            if(liberado && Game.dinheiro >= 10){
                Game.entities.add(player);
                Game.dinheiro-=10;
            } else{
                System.out.println("Sem dinheiro");
            }
        }

        if(Game.vida <= 0.99){
            //GAME OVER
            System.exit(1);
        }
    }
    
}
