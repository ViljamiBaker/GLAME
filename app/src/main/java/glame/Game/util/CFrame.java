package glame.game.util;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CFrame {
    public Vector3f position;
    public Quaternionf rotation;

    public CFrame(Vector3f position, float pitch, float yaw, float roll){
        this.position = position;
        this.rotation = GUTILVB.eulerToQuaternion(pitch, yaw, roll);
    }

    public CFrame(Vector3f position, Quaternionf rotation){
        this.position = position;
        this.rotation = rotation;
    }

    public Matrix4f getAsMat4(){
        return new Matrix4f().translate(position).rotate(rotation);
    }
}
