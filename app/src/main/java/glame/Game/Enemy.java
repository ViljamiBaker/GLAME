package glame.game;

import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.game.util.GUTILVB;
import glame.renderer.model.Model;
import glame.renderer.model.Renderable;
import glame.renderer.util.LUTILVB; 

public class Enemy extends Renderable{
    Vector3f playerpos;
    Vector3f velocity = new Vector3f();
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
        setModel();
    }

    public void update(double dt){
        Vector3f diff = playerpos.sub(cFrame.position, new Vector3f()).normalize();
        cFrame.setRotation(new Vector3f(GUTILVB.vector3toAngles(diff)));
        velocity.add(diff.mul((float)(accel*dt)));
        if(velocity.length()>speed){
            velocity.normalize().mul(speed);
        }
        velocity.mul(1.0f-0.02f*(float)dt);
        cFrame.position.add(velocity);
    }

    @Override
    public void updateModel(){
        model.transform = cFrame.getAsMat4();
    }

    @Override
    public void setModel(){
        model = new Model(LUTILVB.cubeVertices, LUTILVB.cubeIndicies, LUTILVB.defaultTexture, LUTILVB.defaultSpecular);
    }
}
