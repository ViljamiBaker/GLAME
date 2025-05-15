package glame.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.renderer.Renderer;
import glame.renderer.util.Camera;

public class Player {
    public CFrame cframe = new CFrame();

    public Camera camera = new Camera(Renderer.window);

    public Vector3f velocity = new Vector3f();

    double shotCooldown = 0.0;

    float speed = 2.5f;

    public Player(){
        camera.position = cframe.position;
    }

    public void processInput(){
		float speedDT = speed * Renderer.deltaTime; // adjust accordingly
        Vector3f movement = new Vector3f();
		if (glfwGetKey(Renderer.window, GLFW_KEY_W) == GLFW_PRESS){
			movement.add(camera.front.mul(speedDT, new Vector3f()));
		}
		if (glfwGetKey(Renderer.window, GLFW_KEY_S) == GLFW_PRESS){
			movement.sub(camera.front.mul(speedDT, new Vector3f()));
		}
		if (glfwGetKey(Renderer.window, GLFW_KEY_A) == GLFW_PRESS){
			movement.sub((camera.front.cross(camera.up, new Vector3f())).normalize().mul(speedDT,new Vector3f()));
		}
		if (glfwGetKey(Renderer.window, GLFW_KEY_D) == GLFW_PRESS){
			movement.add((camera.front.cross(camera.up, new Vector3f())).normalize().mul(speedDT,new Vector3f()));
		}
        cframe.position.add(movement);

        cframe.setRotation(new Vector3f(0,camera.yaw,camera.pitch));

        shotCooldown -= Renderer.deltaTime;
        
        if(shotCooldown<0.0&&glfwGetMouseButton(Renderer.window, GLFW_MOUSE_BUTTON_1)==GLFW_PRESS){
            shotCooldown = 0.01;
            GameLogic.bullets.add(new Bullet(cframe.position, camera.front, cframe.getRotation(), 5.0));
        }
        if(shotCooldown<0.0&&glfwGetMouseButton(Renderer.window, GLFW_MOUSE_BUTTON_2)==GLFW_PRESS){
            System.out.println(cframe.getRotation().z);
        }
    }
}
