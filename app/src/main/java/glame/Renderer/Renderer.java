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
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.assimp.Assimp;

import glame.renderer.util.*;
import glame.renderer.util.Lights.Light;
//https://learnopengl.com/Lighting/Colors
public class Renderer {
    boolean wireframe = false;
	boolean pDownLast = false;
	boolean lockMouse = true;
	boolean lDownLast = false;
	float deltaTime = 0.0f;	// Time between current frame and last frame
	float lastFrame = 0.0f; // Time of last frame
	Camera camera;
    public Renderer(){

        init();

        long window = createWindow(800, 600, "Colors");
		glEnable(GL_DEPTH_TEST);  
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		enableDebug();

		camera = new Camera(window);
		float vertices[] = {
			// positions          // normals           // texture coords
			-0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f, 0.0f,
			 0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f, 0.0f,
			 0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f, 1.0f,
			 0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  1.0f, 1.0f,
			-0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f, 1.0f,
			-0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,  0.0f, 0.0f,
		
			-0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,   0.0f, 0.0f,
			 0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,   1.0f, 0.0f,
			 0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,   1.0f, 1.0f,
			 0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,   1.0f, 1.0f,
			-0.5f,  0.5f,  0.5f,  0.0f,  0.0f, 1.0f,   0.0f, 1.0f,
			-0.5f, -0.5f,  0.5f,  0.0f,  0.0f, 1.0f,   0.0f, 0.0f,
		
			-0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f, 0.0f,
			-0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  1.0f, 1.0f,
			-0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f, 1.0f,
			-0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,  0.0f, 1.0f,
			-0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  0.0f, 0.0f,
			-0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,  1.0f, 0.0f,
		
			 0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f, 0.0f,
			 0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  1.0f, 1.0f,
			 0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f, 1.0f,
			 0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,  0.0f, 1.0f,
			 0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  0.0f, 0.0f,
			 0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,  1.0f, 0.0f,
		
			-0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f, 1.0f,
			 0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  1.0f, 1.0f,
			 0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f, 0.0f,
			 0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  1.0f, 0.0f,
			-0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,  0.0f, 0.0f,
			-0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,  0.0f, 1.0f,
		
			-0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f, 1.0f,
			 0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  1.0f, 1.0f,
			 0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f, 0.0f,
			 0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  1.0f, 0.0f,
			-0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,  0.0f, 0.0f,
			-0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,  0.0f, 1.0f
		};

		Vector3f[] cubePositions = {
			new Vector3f( 0.0f,  0.0f,  0.0f), 
			new Vector3f( 2.0f,  5.0f, -15.0f), 
			new Vector3f(-1.5f, -2.2f, -2.5f),  
			new Vector3f(-3.8f, -2.0f, -12.3f),  
			new Vector3f( 2.4f, -0.4f, -3.5f),  
			new Vector3f(-1.7f,  3.0f, -7.5f),  
			new Vector3f( 1.3f, -2.0f, -2.5f),  
			new Vector3f( 1.5f,  2.0f, -2.5f), 
			new Vector3f( 1.5f,  0.2f, -1.5f), 
			new Vector3f(-1.3f,  1.0f, -1.5f)  
		};

		Vector3f pointLightPositions[] = {
			new Vector3f( 0.7f,  0.2f,  2.0f),
			new Vector3f( 2.3f, -3.3f, -4.0f),
			new Vector3f(-4.0f,  2.0f, -12.0f),
			new Vector3f( 0.0f,  0.0f, -3.0f)
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

        Shader lightingShader = new Shader("shaderVertex", "shaderFrag");
		
		Shader shaderLC = new Shader("shaderVertex", "shaderFragLC");

		Texture diffuseMap = new Texture("container2.png");
		diffuseMap.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		Texture specularMap = new Texture("container2_specular.png");
		specularMap.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        while(!glfwWindowShouldClose(window))
		{
			float currentFrame = (float)glfwGetTime();
			deltaTime = currentFrame - lastFrame;
			lastFrame = currentFrame;  

			// input
			processInput(window);

			//render code		
			//new Vector3f(1,1,3);
			Vector3f lightPos = new Vector3f();//(float)Math.sin(currentFrame)*3.0f,(float)Math.sin(currentFrame*2),(float)Math.cos(currentFrame)*3.0f);

			glClearColor(0.2f, 0.3f, 0.3f, 0f);
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

			lightingShader.setInt("DIR_LIGHT_COUNT", 1);
			lightingShader.setInt("POINT_LIGHT_COUNT", pointLightPositions.length);
			lightingShader.setInt("SOPT_LIGHT_COUNT", 1);

			lightingShader.setInt("material.diffuse", 0);
			lightingShader.setInt("material.specular", 1);
			lightingShader.setFloat("material.shininess", 32.0f);

			lightingShader.setUniform("objectColor", 0.5f, 0.5f, 0.31f);

			Light dirLight = Lights.createDirLight(new Vector3f(-0.2f, -1.0f, -0.3f), new Vector3f(0.05f, 0.05f, 0.05f), new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(0.5f, 0.5f, 0.5f));

			dirLight.setShaderValues(lightingShader);

			Light spotLight = Lights.createSpotLight(camera.position, camera.front, new Vector3f( 0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f), 
				1.0f, 0.09f, 0.032f, (float)Math.cos(Math.toRadians(12.5f)), (float)Math.cos(Math.toRadians(15.0f)));

			spotLight.setShaderValues(lightingShader);

			for (int i = 0; i < pointLightPositions.length; i++) {
				Light pointLight = Lights.createPointLight(pointLightPositions[i], new Vector3f(0.05f, 0.05f, 0.05f), 
					new Vector3f(0.8f, 0.8f, 0.8f), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f,0.3f,0.5f);
				pointLight.setShaderValues(lightingShader);
			}

			Lights.resetIndices();

			glActiveTexture(GL_TEXTURE0);
			diffuseMap.bind();
			glActiveTexture(GL_TEXTURE1);
			specularMap.bind();

			// world transformation
			Matrix4f model = new Matrix4f();
			for (int i = 0; i < cubePositions.length; i++) {
				float angle = 20.0f * i + (float)glfwGetTime(); 
				model = new Matrix4f().translate(cubePositions[i]).rotate(angle, new Vector3f(1.0f, 0.3f, 0.5f).normalize()); 
				lightingShader.setUniform("model", model);
				lightingShader.setFloat("mixpercent", (float)Math.sin(angle)/2.0f+0.5f);
				//shader.setFloat("time", (float)glfwGetTime());
				glBindVertexArray(cubeVertexArray);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}


			// also draw the lamp object
			shaderLC.use();
			shaderLC.setUniform("projection", projection);
			shaderLC.setUniform("view", view);
			glBindVertexArray(lightCubeVertexArray);
			for (int i = 0; i < 4; i++)
			{
				model = new Matrix4f();
				model = model.translate(pointLightPositions[i]);
				model = model.scale(new Vector3f(0.2f)); // Make it a smaller cube
				shaderLC.setUniform("model", model);
				glDrawArrays(GL_TRIANGLES, 0, 36);
			}

			

			// check events and swap buffers
			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		glfwTerminate();
    }

    public void processInput(long window)
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
		if(lockMouse){
			camera.processInput(deltaTime);
		}
	}

    public static void main(String[] args) {
        new Renderer();
    }
}
