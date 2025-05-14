package glame.game;

import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.game.util.GUTILVB;

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
        Vector3f diff = playerpos.sub(cFrame.position, new Vector3f()).normalize();
        cFrame.setRotation(new Vector3f(GUTILVB.vector3toAngles(diff)));
        cFrame.position.add(diff.mul((float)(speed*dt)));
    }
}
