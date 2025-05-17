package glame.game;

import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.game.util.GUTILVB;

public class Enemy {
    Vector3f playerpos;
    Vector3f velocity = new Vector3f();;
    CFrame cFrame;
    double health;
    float speed;
    float accel;

    public Enemy(CFrame cFrame, double health, float speed, Vector3f playerpos){
        this.cFrame = cFrame;
        this.health = health;
        this.speed = speed;
        this.playerpos = playerpos;
        accel = speed/30.0f;
    }

    public void update(double dt){
        Vector3f diff = playerpos.sub(cFrame.position, new Vector3f()).normalize();
        cFrame.setRotation(new Vector3f(GUTILVB.vector3toAngles(diff)));
        velocity.add(diff.mul((float)(accel*dt)));
        if(velocity.length()>speed){
            velocity.normalize().mul(speed);
        }
        cFrame.position.add(velocity);
    }
}
