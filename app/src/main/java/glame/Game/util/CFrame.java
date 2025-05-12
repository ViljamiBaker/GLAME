package glame.game.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CFrame {
    public Vector3f position;
    public Vector3f rotation;
    public float scale;

    public CFrame(){
        position = new Vector3f();
        rotation = new Vector3f();
        scale = 1.0f;
    }

    public CFrame(CFrame cloneFrom){
        position = new Vector3f().add(cloneFrom.position);
        rotation = new Vector3f().add(cloneFrom.rotation);
        scale = cloneFrom.scale;
    }

    public CFrame(Vector3f position, float pitch, float yaw, float roll, float scale){
        this.position = position;
        this.rotation = new Vector3f(pitch, yaw, roll);
        this.scale = scale;
    }

    public CFrame(Vector3f position, Vector3f rotation, float scale){
        this.position = new Vector3f(position);
        this.rotation = new Vector3f(rotation);
        this.scale = scale;
    }

    /*public CFrame(Vector3f position, Quaternionf rotation, float scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }*/

    public Matrix4f getAsMat4(){
        return new Matrix4f().translate(position).rotateXYZ(rotation).scale(scale);
    }
}
