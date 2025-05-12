package glame.game.util;

import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class GUTILVB {
    public static Quaternionf eulerToQuaternion(Vector3f in){
        return eulerToQuaternion(in.x, in.y, in.z);
    }
    public static Quaternionf eulerToQuaternion(float pitch, float yaw, float roll){
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);

        double w = cr * cp * cy + sr * sp * sy;
        double x = sr * cp * cy - cr * sp * sy;
        double y = cr * sp * cy + sr * cp * sy;
        double z = cr * cp * sy - sr * sp * cy;
        return new Quaternionf(x, y, z, w);
    }

    public static float[] vector3toAngles(Vector3f in){
        float r = Math.sqrt(in.x*in.x+in.y*in.y+in.z*in.z);
        float pitch = Math.atan2(in.y,in.x);
        float yaw = Math.acos(in.z/r);

        return new float[] {pitch,yaw,0};
    }

    public static Quaternionf rotateQuatByEuler(Quaternionf quat, float pitch, float yaw, float roll){
        return quat.add(eulerToQuaternion(pitch, yaw, roll));
    }

    public static Quaternionf rotateQuatByEuler(Quaternionf quat, float pitch, float yaw, float roll, Quaternionf dest){
        return quat.add(eulerToQuaternion(pitch, yaw, roll), dest);
    }
}
