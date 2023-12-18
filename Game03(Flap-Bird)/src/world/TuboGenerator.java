package world;

import entities.Entity;
import entities.Tubo;
import main.Game;

public class TuboGenerator {
    
    public int time = 0;
    public int targettime = 60;

    public void tick(){
        time++;
        if(time == 60){
            time = 0;
            int altura1 = Entity.rand.nextInt(120 - 20) + 20;
            int altura2 = Entity.rand.nextInt(120 - 20) + 20;

            if(altura1 + altura2 > 210){
                altura1 = Entity.rand.nextInt(120 - 20);
                altura2 = Entity.rand.nextInt(120 - 20);
            }

            Tubo tubo1 = new Tubo(Game.WIDTH, 0, 20, altura1, 1 , Game.spritesheet.getSprite(48, 0, 16, 16));
            Tubo tubo2 = new Tubo(Game.WIDTH, Game.HEIGHT - altura2, 20, altura2, 1 , Game.spritesheet.getSprite(32, 0, 16, 16));

            Game.entities.add(tubo1);
            Game.entities.add(tubo2);
        }
    }

}
