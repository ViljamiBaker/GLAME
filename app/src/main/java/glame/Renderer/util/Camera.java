package glame.renderer.util;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	public Vector3f position = new Vector3f(0.0f, 0.0f,  3.0f);
	public Vector3f front = new Vector3f(0.0f, 0.0f, -1.0f);
	public Vector3f up = new Vector3f(0.0f, 1.0f,  0.0f);
	float fov = 1.0f;
	float yaw = 0;
	float pitch = 0;
	float lastX = 400, lastY = 300;
	boolean firstMouse = true;
    long window;

	boolean projectionGood = false;

	boolean mouseMoveEnabled = true;
    public Camera(long window){
        this.window = window;
        glfwSetCursorPosCallback(window, (windowInner, xpos, ypos)->{
			if(!mouseMoveEnabled)return;
			if (firstMouse) // initially set to true
			{
				lastX = (float)xpos;
				lastY = (float)ypos;
				firstMouse = false;
			}

			float xoffset = (float)xpos - lastX;
			float yoffset = lastY - (float)ypos;
			lastX = (float)xpos;
			lastY = (float)ypos;
			
			float sensitivity = 0.002f;
			xoffset *= sensitivity;
			yoffset *= sensitivity;

			yaw   += xoffset;
			pitch += yoffset;  

			if(pitch > 1.55f)
			pitch =  1.55f;
			if(pitch < -1.55f)
			pitch = -1.55f;

			Vector3f direction = new Vector3f();
			direction.x = (float)Math.cos(yaw) * (float)Math.cos(pitch);
			direction.y = (float)Math.sin(pitch);
			direction.z = (float)Math.sin(yaw) * (float)Math.cos(pitch);
			front = direction.normalize();
		}); 
    }

    public Matrix4f getProjection(){
		projectionGood = true;
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(window, width, height);
        return new Matrix4f().perspective(fov, (float)width[0]/(float)height[0], 0.01f, 100.0f);
    }

	public Matrix4f getVeiw(){
        return new Matrix4f().lookAt(position, position.add(front, new Vector3f()), up);
    }

    public void processInput(float deltaTime){
		float cameraSpeed = 2.5f * deltaTime; // adjust accordingly
		if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS){
			position.add(front.mul(cameraSpeed, new Vector3f()));
		}
		if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS){
			position.sub(front.mul(cameraSpeed, new Vector3f()));
		}
		if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS){
			position.sub((front.cross(up, new Vector3f())).normalize().mul(cameraSpeed,new Vector3f()));
		}
		if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS){
			position.add((front.cross(up, new Vector3f())).normalize().mul(cameraSpeed,new Vector3f()));
		}
		if(glfwGetKey(window, GLFW_KEY_R)==GLFW_PRESS){
			fov += 0.05f;
			projectionGood = false;
		}
		if(glfwGetKey(window, GLFW_KEY_F)==GLFW_PRESS){
			fov -= 0.05f;
			projectionGood = false;
		}
    }

	public boolean projectionGood(){
		return projectionGood;
	}
	public void setMouseMoveEnabled(boolean mouseMoveEnabled) {
		this.mouseMoveEnabled = mouseMoveEnabled;
		if(mouseMoveEnabled){
			firstMouse = true;
		}
	}
}
