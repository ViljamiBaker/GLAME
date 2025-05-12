package glame.game;

import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.game.util.GUTILVB;

public class Bullet {
    CFrame cFrame;

    Vector3f forward;

    double speed;

    public Bullet(Vector3f position,Vector3f forward, double speed){
        float[] rotation = GUTILVB.vector3toAngles(forward);
        System.out.println(rotation[0]*57.295 + " "+ rotation[1]*57.295 + " " + rotation[2]*57.295);
        this.cFrame = new CFrame(position, forward.x, forward.y, forward.z, 0.1f);
        this.forward = forward;
        this.speed = speed;
    }

    public void update(double dt){
        cFrame.position.add(forward.mul((float)(dt*speed), new Vector3f()));
    }

    public CFrame getCFrame(){
        return cFrame;//new CFrame(cFrame.position, cFrame.rotation, cFrame.scale);
    }
}
