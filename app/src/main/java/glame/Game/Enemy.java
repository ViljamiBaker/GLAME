package glame.game;

import org.joml.Vector3f;

import glame.game.util.CFrame;

public class Enemy {
    Vector3f playerpos;
    CFrame cFrame;
    double health;
    float speed;

    public Enemy(CFrame cFrame, double health, float speed, Vector3f playerpos){
        this.cFrame = cFrame;
        this.health = health;
        this.speed = speed;
        this.playerpos = playerpos;
    }

    public void update(double dt){
        cFrame.position.add(playerpos.sub(cFrame.position, new Vector3f()).normalize().mul((float)(speed*dt)));
    }
}
