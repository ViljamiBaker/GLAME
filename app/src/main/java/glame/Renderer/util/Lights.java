package glame.renderer.util;

import org.joml.Vector3f;

public class Lights {
    private Lights(){}

    static int dirLightIndex = 0;
    static int pointLightIndex = 0;
    static int spotLightIndex = 0;

    public static void resetIndices(){
        dirLightIndex = 0;
        pointLightIndex = 0;
        spotLightIndex = 0;
    }

    public static class Light{
        public void setShaderValues(Shader shader, int index){}
        public void setShaderValues(Shader shader){}
    }

    public static Light createDirLight(Vector3f direction, Vector3f ambient, Vector3f diffuse, Vector3f specular){
        return new DirLight(direction, ambient, diffuse, specular);
    }

    public static Light createPointLight(Vector3f position, Vector3f ambient, Vector3f diffuse, Vector3f specular, float constant, float linear, float quadratic){
        return new PointLight(position, constant, linear, quadratic, ambient, diffuse, specular);
    }

    public static Light createSpotLight(Vector3f position, Vector3f direction, Vector3f ambient, Vector3f diffuse, 
    Vector3f specular, float constant, float linear, float quadratic, float cutOff, float outerCutOff){
        return new SpotLight(position, direction, cutOff, outerCutOff, constant, linear, quadratic, ambient, diffuse, specular);
    }

    private static class DirLight extends Light{
        Vector3f direction;
	
        Vector3f ambient;
        Vector3f diffuse;
        Vector3f specular;
        public DirLight(Vector3f direction, Vector3f ambient, Vector3f diffuse, Vector3f specular){
            this.direction = direction;
            this.ambient = ambient;
            this.diffuse = diffuse;
            this.specular = specular;
        }

        @Override
        public void setShaderValues(Shader shader, int index){
            shader.setUniform("dirLights[" + index +"].direction", direction);
			shader.setUniform("dirLights[" + index +"].ambient", ambient);
			shader.setUniform("dirLights[" + index +"].diffuse", diffuse);
			shader.setUniform("dirLights[" + index +"].specular", specular);
        }

        @Override
        public void setShaderValues(Shader shader){
            setShaderValues(shader, dirLightIndex);
            dirLightIndex++;
        }
    }

    private static class PointLight extends Light{
        Vector3f position;
    
        float constant;
        float linear;
        float quadratic;
        
        Vector3f ambient;
        Vector3f diffuse;
        Vector3f specular;
        public PointLight(Vector3f position, float constant, float linear, float quadratic, Vector3f ambient, Vector3f diffuse, Vector3f specular){
            this.position = position;
            this.constant = constant;
            this.linear = linear;
            this.quadratic = quadratic;
            this.ambient = ambient;
            this.diffuse = diffuse;
            this.specular = specular;
        }

        @Override
        public void setShaderValues(Shader shader, int index){
            shader.setUniform("pointLights["+ index +"].position", position);
            shader.setUniform("pointLights["+ index +"].ambient", ambient);
            shader.setUniform("pointLights["+ index +"].diffuse", diffuse);
            shader.setUniform("pointLights["+ index +"].specular", specular);
            shader.setFloat("pointLights["+ index +"].constant", constant);
            shader.setFloat("pointLights["+ index +"].linear", linear);
            shader.setFloat("pointLights["+ index +"].quadratic", quadratic);
        }

        @Override
        public void setShaderValues(Shader shader){
            setShaderValues(shader, pointLightIndex);
            pointLightIndex++;
        }
    }

    private static class SpotLight extends Light{
        Vector3f position;
        Vector3f direction;
        float cutOff;
        float outerCutOff;
      
        float constant;
        float linear;
        float quadratic;
    
        Vector3f ambient;
        Vector3f diffuse;
        Vector3f specular;
        public SpotLight(Vector3f position, Vector3f direction, float cutOff, float outerCutOff, float constant, 
                        float linear, float quadratic, Vector3f ambient, Vector3f diffuse, Vector3f specular){
            this.position = position;
            this.direction = direction;
            this.cutOff = cutOff;
            this.outerCutOff = outerCutOff;
            this.constant = constant;
            this.linear = linear;
            this.quadratic = quadratic;
            this.ambient = ambient;
            this.diffuse = diffuse;
            this.specular = specular;
        }

        @Override
        public void setShaderValues(Shader shader, int index){
            shader.setUniform("spotLights[" + index +"].position", position);
			shader.setUniform("spotLights[" + index +"].direction", direction);
			shader.setUniform("spotLights[" + index +"].ambient", ambient);
			shader.setUniform("spotLights[" + index +"].diffuse", diffuse);
			shader.setUniform("spotLights[" + index +"].specular", specular);
			shader.setFloat("spotLights[" + index +"].constant", constant);
			shader.setFloat("spotLights[" + index +"].linear", linear);
			shader.setFloat("spotLights[" + index +"].quadratic", quadratic);
			shader.setFloat("spotLights[" + index +"].cutOff", cutOff);
			shader.setFloat("spotLights[" + index +"].outerCutOff", outerCutOff);  
        }

        @Override
        public void setShaderValues(Shader shader){
            setShaderValues(shader, spotLightIndex);
            spotLightIndex++;
        }
    }
}
