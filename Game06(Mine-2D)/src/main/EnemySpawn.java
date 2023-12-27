package main;

import entities.Enemy;
import entities.Entity;

public class EnemySpawn {
    
    public int interval = 60*10;
    public int curTime = 0;

    public void tick(){
        curTime++;
        if(curTime == interval){
            curTime = 0;
            Enemy enemy = new Enemy(16, 16, 16, 16, 1, Entity.Enemy_RIGHT, Entity.Enemy_LEFT);
            Game.entities.add(enemy);
        }
    }

}
