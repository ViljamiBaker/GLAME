package glame.game;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.game.util.GUTILVB;
import glame.renderer.util.Lights;
import glame.renderer.util.Lights.Light;

public class Bullet {
    CFrame cFrame;

    Vector3f forward;

    Light l;

    double speed;
    //)new Vector3f(0, -dir.y, dir.z)
    //(float)Math.PI/4.0f
    //.rotateAxis(-dir.y, 0, 1, 0)
    public Bullet(Vector3f pos, Vector3f front, double speed){

        this.cFrame = new CFrame(pos.add(front, new Vector3f()), new Quaternionf().identity(), 0.2f);
        cFrame.setRotation(new Vector3f(GUTILVB.vector3toAngles(front)));
        //cFrame.rotate(dir);//.mul(dir.conjugate(new Quaternionf()));//.mul(
            //new Quaternionf().rotateX((float)Math.PI/4.0f).rotateAxis((float)Math.PI/4.0f, new Vector3f(0,1,1).normalize())));
        this.forward = front;
        this.speed = speed;
        l = Lights.createPointLight(pos, new Vector3f(1,1,1), new Vector3f(1,1,1), new Vector3f(1,1,1), 1.0f,0.3f,0.5f);
    }

    public void update(double dt){
        cFrame.position.add(forward.mul((float)(dt*speed), new Vector3f()));
        l.setPosition(forward);
    }

    public CFrame getCFrame(){
        return new CFrame(cFrame.position, cFrame.rotation, cFrame.scale);
    }
}
