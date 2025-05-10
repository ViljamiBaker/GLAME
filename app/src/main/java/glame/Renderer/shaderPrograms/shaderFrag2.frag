#version 330 core
out vec4 FragColor;
  
in vec3 vertexCoord;

in vec3 vertexColor;

void main()
{
    vec3 col = vertexColor;//vertexCoord.xyx;
    FragColor = vec4(col,0.5);
} 