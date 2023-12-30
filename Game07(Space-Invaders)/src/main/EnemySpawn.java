package main;

import entities.Enemy;
import entities.Entity;

public class EnemySpawn {
    
    public int targetTime = 60*2;
    public int curTime = 0;
    private int randomMultiplier = 0;

    public void tick(){
        curTime++;
        if(curTime == targetTime){
            curTime=0;
            randomMultiplier = Enemy.rand.nextInt(3);
            int yy = 0;
            int xx = Entity.rand.nextInt(Game.WIDTH-16);
            Enemy enemy = new Enemy(xx, yy, 16,16, 1, Game.spritesheet.getSprite(16*randomMultiplier, 16, 16, 16));
            Game.entities.add(enemy);
        }
    }

}
