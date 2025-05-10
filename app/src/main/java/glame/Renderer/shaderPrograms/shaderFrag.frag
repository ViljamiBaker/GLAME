#version 330 core
out vec4 FragColor;
  
in vec3 vertexCoord;

uniform float time;

void main()
{
    vec3 col = 0.5 + 0.5*cos(time+vertexCoord.xyx+vec3(0,2,4));
    FragColor = vec4(col,0.5);
} 