package glame.game;

import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.game.util.GUTILVB;
import glame.renderer.util.Camera;

public class Bullet {
    CFrame cFrame;

    Vector3f forward;

    double speed;

    public Bullet(Camera c, double speed){
        this.cFrame = new CFrame(c.position.add(c.front, new Vector3f()), new Vector3f(0, -c.yaw, c.pitch), 0.2f);
        this.forward = new Vector3f(GUTILVB.eulerAngToVector3(cFrame.rotation.z, -cFrame.rotation.y));
        this.speed = speed;
    }

    public void update(double dt){
        cFrame.position.add(forward.mul((float)(dt*speed), new Vector3f()));
    }

    public CFrame getCFrame(){
        return new CFrame(cFrame.position, cFrame.rotation, cFrame.scale);
    }
}
