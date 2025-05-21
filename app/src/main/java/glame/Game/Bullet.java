package glame.game;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.game.util.GUTILVB;
import glame.renderer.model.Model;
import glame.renderer.model.Renderable;
import glame.renderer.util.LUTILVB;

public class Bullet extends Renderable{
    CFrame cFrame;

    Vector3f forward;

    double speed;
    public Bullet(Vector3f pos, Vector3f front, double speed){
        this.cFrame = new CFrame(pos.add(front, new Vector3f()), new Quaternionf().identity(), 0.2f);
        cFrame.setRotation(new Vector3f(GUTILVB.vector3toAngles(front)));
        cFrame.rotate(0, (float)Math.PI/4, 0);
        cFrame.rotate((float)Math.PI/4, 0, 0);
        this.forward = front;
        this.speed = speed;
    }

    public void update(double dt){
        cFrame.position.add(forward.mul((float)(dt*speed), new Vector3f()));
    }

    public CFrame getCFrame(){
        return new CFrame(cFrame.position, cFrame.rotation, cFrame.scale);
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
