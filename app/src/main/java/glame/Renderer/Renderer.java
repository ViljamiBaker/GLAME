package glame.renderer;
import static glame.renderer.util.LUTILVB.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.Arrays;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import glame.game.GameLogic;
import glame.game.util.CFrame;
import glame.renderer.util.*;
import glame.renderer.util.Lights.Light;
//https://learnopengl.com/Lighting/Colors
public class Renderer {
	public static float deltaTime = 0.0f;	// Time between current frame and last frame
	public static Camera camera;
	public static long window;
	
    static boolean wireframe = false;
	static boolean pDownLast = false;
	static boolean lockMouse = true;
	static boolean lDownLast = false;
	static float lastFrame = 0.0f; // Time of last frame
	static ArrayList<Float> lineData = new ArrayList<>();

    public static void start(){
        init();

        window = createWindow(800, 600, "Colors");
		glEnable(GL_DEPTH_TEST);  
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		enableDebug();


		GameLogic.init();

		camera = GameLogic.player.camera;

		float vertices[] = LUTILVB.cubeVertices;

		Vector3f pointLightPositions[] = {
			new Vector3f( 0.7f,  0.2f,  2.0f),
			new Vector3f( 2.3f, -3.3f, -4.0f),
			new Vector3f(-4.0f,  2.0f, -12.0f),
			new Vector3f( 0.0f,  0.0f, -3.0f),
			new Vector3f( 0.0f,  0.0f, 0.0f)
		};  

		Matrix4f projection = new Matrix4f().perspective(1.0f, 300.0f / 300.0f, 0.1f, 100.0f);
		Matrix4f view = new Matrix4f();

		int cubeVertexArray = glGenVertexArrays();
		int vertexBuffer = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
    	glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		glBindVertexArray(cubeVertexArray);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES*8, 0);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, Float.BYTES*8, Float.BYTES*3);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, Float.BYTES*8, Float.BYTES*6);
		glEnableVertexAttribArray(2);

		int lightCubeVertexArray = glGenVertexArrays();

		glBindVertexArray(lightCubeVertexArray);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES*8, 0);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, Float.BYTES*8, Float.BYTES*3);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, Float.BYTES*8, Float.BYTES*6);
		glEnableVertexAttribArray(2);

        Shader lightingShader = new Shader("shaderVertex", "shaderFrag");
		Shader shaderLC = new Shader("shaderVertex", "shaderFragTexture");
		Shader lineShader = new Shader("shaderLineVertex", "shaderLineFrag");

		Texture diffuseMap = new Texture("CubeTexture.png");
		Texture specularMap = new Texture("container2_specular.png");
		Texture lcTexture = new Texture("LCTexture.png");
		Texture stars = new Texture("Stars.png");

		Light spotLight = Lights.createSpotLight(camera.position, camera.front, new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), 
			1.0f, 0.045f, 0.0075f, (float)Math.cos(Math.toRadians(20.0f)), (float)Math.cos(Math.toRadians(30.0f)));
		
		Lights.createDirLight(new Vector3f(-0.2f, -1.0f, -0.3f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(0.9f, 0.9f, 0.9f));

		for (int i = 0; i < pointLightPositions.length; i++) {
			Lights.createPointLight(pointLightPositions[i], 
				new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f,0.3f,0.5f);
		}
        while(!glfwWindowShouldClose(window))
		{
			float currentFrame = (float)glfwGetTime();
			deltaTime = currentFrame - lastFrame;
			lastFrame = currentFrame;  

			GameLogic.update();

			// input
			processInput(window);

			//render code

			glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			lightingShader.use();
			if(!camera.projectionGood()){
				projection = camera.getProjection();
				lightingShader.setUniform("projection", projection);
			}
			
			view = camera.getVeiw();

			lightingShader.setUniform("view", view);

			// be sure to activate shader when setting uniforms/drawing objects
			lightingShader.use();		

			lightingShader.setInt("material.diffuse", 0);
			lightingShader.setInt("material.specular", 1);
			lightingShader.setFloat("material.shininess", 32.0f);

			lightingShader.setUniform("objectColor", 0.5f, 0.5f, 0.31f);

			spotLight.setDirection(camera.front);
			spotLight.setPosition(camera.position);

			Lights.setLights(lightingShader);

			glActiveTexture(GL_TEXTURE0);
			diffuseMap.bind();
			glActiveTexture(GL_TEXTURE1);
			specularMap.bind();

			CFrame[] cubePositions = GameLogic.getCubes();
			// world transformation
			Matrix4f model = new Matrix4f();
			for (int i = 0; i < cubePositions.length; i++) {
				model = cubePositions[i].getAsMat4(); 
				lightingShader.setUniform("model", model);
				glBindVertexArray(cubeVertexArray);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}


			// also draw the lamp object
			shaderLC.use();
			shaderLC.setUniform("projection", projection);
			shaderLC.setUniform("view", view);
			glActiveTexture(GL_TEXTURE0);
			lcTexture.bind();
			glBindVertexArray(lightCubeVertexArray);
			for (int i = 0; i < 4; i++)
			{
				model = new Matrix4f();
				model = model.translate(pointLightPositions[i]);
				model = model.scale(new Vector3f(0.2f)); // Make it a smaller cube
				shaderLC.setUniform("model", model);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}

			glActiveTexture(GL_TEXTURE0);
			stars.bind();

			model = new CFrame(new Vector3f(0), (float)(Math.sin(glfwGetTime()/20.0)), (float)(Math.cos(glfwGetTime()/33.3)), lightCubeVertexArray, 1000).getAsMat4(); 
			shaderLC.setUniform("model", model);
			//glDrawArrays(GL_TRIANGLES, 0, 36);
			
			drawLine(new Vector3f(0.0f,0.0f,1.0f), new Vector3f(0.5f,0.5f,0.0f), new Vector3f(1.0f));
			//drawLine(new Vector3f(0,0,0), new Vector3f(0,1,0), new Vector3f(0,1,0));
			//drawLine(new Vector3f(0,0,0.1f), new Vector3f(0,0,1), new Vector3f(0,0,1));

			lineShader.use();
			lineShader.setUniform("projection", projection);
			lineShader.setUniform("view", view);
			drawLinesFlush();

			// check events and swap buffers
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		glfwTerminate();
    }

	static int lineVAO, lineVBO;

	static void drawLine(Vector3f p1, Vector3f p2, Vector3f color)
	{
		// point 1
		lineData.add(p1.x);
		lineData.add(p1.y);
		lineData.add(p1.z);

		// color
		lineData.add(color.x);
		lineData.add(color.y);
		lineData.add(color.z);

		// point 2
		lineData.add(p2.x);
		lineData.add(p2.y);
		lineData.add(p2.z);

		// color
		lineData.add(color.x);
		lineData.add(color.y);
		lineData.add(color.z);
	}
	static void drawLine2d(Vector2f p1, Vector2f p2, Vector3f color)
	{
		drawLine(new Vector3f(p1, 0), new Vector3f(p2, 0), color);
	}

	static boolean created = false;

	static void drawLinesFlush()
	{


		double[] arr = lineData.stream().mapToDouble(f -> f != null ? f : Float.NaN).toArray();

		if (!created)
		{
			created = true;

			lineVAO = glGenVertexArrays();

			lineVBO = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, lineVBO);
			glBufferData(GL_ARRAY_BUFFER, arr, GL_STATIC_DRAW);

			glBindVertexArray(lineVAO);

			glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES*6,0);
			glEnableVertexAttribArray(0);

			glVertexAttribPointer(1, 3, GL_FLOAT, false, Float.BYTES*6,Float.BYTES*3);
			glEnableVertexAttribArray(1);
		}
		else
		{
			glBindBuffer(GL_ARRAY_BUFFER, lineVBO);
			glBufferData(GL_ARRAY_BUFFER, arr, GL_STATIC_DRAW);
		}

		System.out.println(Arrays.toString(arr));

		// 6 floats make up a vertex (3 position 3 color)
		// divide by that to get number of vertices to draw
		int count = lineData.size()/6;

		glBindVertexArray(lineVAO);
		glDrawArrays(GL_LINES, 0, count);

		lineData.clear();
	}

    public static void processInput(long window)
	{
		if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
			glfwSetWindowShouldClose(window, true);
		
		if(glfwGetKey(window, GLFW_KEY_P) == GLFW_PRESS&&!pDownLast){
			if(!wireframe){
				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			}else{
				glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			}
			wireframe = !wireframe;
		}
		if(glfwGetKey(window, GLFW_KEY_L) == GLFW_PRESS&&!lDownLast){
			if(lockMouse){
				lockMouse = false;
				glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
			}else{
				lockMouse = true;
				glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
			}
			camera.setMouseMoveEnabled(lockMouse);
		}
		lDownLast = glfwGetKey(window, GLFW_KEY_L) == GLFW_PRESS;
		pDownLast = glfwGetKey(window, GLFW_KEY_P) == GLFW_PRESS;
	}

    public static void main(String[] args) {
        Renderer.start();
    }
}