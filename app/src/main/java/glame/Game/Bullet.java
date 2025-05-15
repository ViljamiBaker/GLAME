package glame.game;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import glame.game.util.CFrame;

public class Bullet {
    CFrame cFrame;

    Vector3f forward;

    double speed;
    //)new Vector3f(0, -dir.y, dir.z)
    //(float)Math.PI/4.0f
    //.rotateAxis(-dir.y, 0, 1, 0)
    public Bullet(Vector3f pos, Vector3f front, Vector3f dir, double speed){

        this.cFrame = new CFrame(pos.add(front, new Vector3f()), new Quaternionf(), 0.2f);
        cFrame.rotation.rotateY(-dir.y).mul(new Quaternionf().rotateZ(dir.z));//.mul(
            //new Quaternionf().rotateX((float)Math.PI/4.0f).rotateAxis((float)Math.PI/4.0f, new Vector3f(0,1,1).normalize())));
        this.forward = front;
        this.speed = speed;
    }

    public void update(double dt){
        cFrame.position.add(forward.mul((float)(dt*speed), new Vector3f()));
    }

    public CFrame getCFrame(){
        return new CFrame(cFrame.position, cFrame.rotation, cFrame.scale);
    }
}
