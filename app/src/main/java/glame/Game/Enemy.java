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
        Vector3f diff = playerpos.sub(cFrame.position, new Vector3f()).normalize().mul((float)(speed*dt));
        cFrame.rotation = diff.normalize(new Vector3f());
        cFrame.position.add(diff);
    }
}
