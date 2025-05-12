package glame.game;

import org.joml.Vector3f;

import glame.game.util.CFrame;

public class Bullet {
    CFrame cFrame;

    Vector3f forward;

    double speed;

    public Bullet(Vector3f pos, Vector3f front, Vector3f dir, double speed){
        this.cFrame = new CFrame(pos.add(front, new Vector3f()), new Vector3f(0, -dir.y, dir.z), 0.2f);
        this.forward = front;
        this.speed = speed;
    }

    public void update(double dt){
        cFrame.position.add(forward.mul((float)(dt*speed), new Vector3f()));
    }

    public CFrame getCFrame(){
        return new CFrame(cFrame.position, cFrame.rotation.rotateAxis((float)Math.PI/4.0f, forward.x, forward.y, forward.z, new Vector3f()), cFrame.scale);
    }
}
