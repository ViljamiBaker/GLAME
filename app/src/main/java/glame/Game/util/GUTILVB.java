package glame.game.util;

import org.joml.Quaternionf;

public class GUTILVB {
    public static Quaternionf eulerToQuaternion(float pitch, float yaw, float roll){
        double qx = Math.sin(roll/2) * Math.cos(pitch/2) * Math.cos(yaw/2) - Math.cos(roll/2) * Math.sin(pitch/2) * Math.sin(yaw/2);
        double qy = Math.cos(roll/2) * Math.sin(pitch/2) * Math.cos(yaw/2) + Math.sin(roll/2) * Math.cos(pitch/2) * Math.sin(yaw/2);
        double qz = Math.cos(roll/2) * Math.cos(pitch/2) * Math.sin(yaw/2) - Math.sin(roll/2) * Math.sin(pitch/2) * Math.cos(yaw/2);
        double qw = Math.cos(roll/2) * Math.cos(pitch/2) * Math.cos(yaw/2) + Math.sin(roll/2) * Math.sin(pitch/2) * Math.sin(yaw/2);
        return new Quaternionf(qx, qy, qz, qw);
    }

    public static Quaternionf rotateQuatByEuler(Quaternionf quat, float pitch, float yaw, float roll){
        return quat.add(eulerToQuaternion(pitch, yaw, roll));
    }
}
