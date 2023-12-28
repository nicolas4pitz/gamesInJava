package main;

import entities.Enemy;
import entities.Entity;
import world.World;

public class EnemySpawn {
    
    public int interval = 60*2;
    public int curTime = 0;

    public void tick(){
        curTime++;
        if(curTime == interval){
            curTime = 0;
            int xinitial = Entity.rand.nextInt(World.WIDTH*16 - 16 - 16) + 16;
            Enemy enemy = new Enemy(xinitial, 16, 16, 16, 1, Entity.Enemy_RIGHT, Entity.Enemy_LEFT);
            Game.entities.add(enemy);
        }
    }

}
