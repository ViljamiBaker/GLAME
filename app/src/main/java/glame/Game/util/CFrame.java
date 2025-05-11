package glame.game.util;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CFrame {
    public Vector3f position;
    public Quaternionf rotation;
    public float scale;

    public CFrame(CFrame cloneFrom){
        position = new Vector3f().add(cloneFrom.position);
        rotation = new Quaternionf().add(cloneFrom.rotation);
        scale = cloneFrom.scale;
    }

    public CFrame(Vector3f position, float pitch, float yaw, float roll, float scale){
        this.position = position;
        this.rotation = GUTILVB.eulerToQuaternion(pitch, yaw, roll);
        this.scale = scale;
    }

    public CFrame(Vector3f position, Quaternionf rotation, float scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Matrix4f getAsMat4(){
        return new Matrix4f().translate(position).rotate(rotation).scale(scale);
    }
}
