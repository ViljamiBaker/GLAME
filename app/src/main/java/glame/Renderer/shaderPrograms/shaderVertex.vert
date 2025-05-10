#version 330 core
in vec3 aPos;
  
out vec3 vertexCoord;

void main()
{
    gl_Position = vec4(aPos, 1.0);
    vertexCoord = aPos;
}