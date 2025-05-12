package glame.game;


import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.renderer.util.Camera;

public class GameLogic {
    Camera player;

    double shotCooldown = 0.0;

    long window;

    double timeSinceLastSpawn = 10000;

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();

    Random r = new Random();

    public GameLogic(Camera player, long window){
        this.player = player;
        this.window = window;
    }

    public void update(double dt){
        shotCooldown -= dt;
        processInput(dt);
        if(timeSinceLastSpawn > 1){
            timeSinceLastSpawn = 0;
            enemies.add(new Enemy(new CFrame(new Vector3f((float)r.nextDouble(-25, 25),(float)r.nextDouble(-25, 25),(float)r.nextDouble(-25, 25)),
                    0,0,0,1), 10, r.nextFloat(0.5f, 2), player.position));
        }
        timeSinceLastSpawn += dt;
        ArrayList<Bullet> bullesToRem = new ArrayList<>();
        ArrayList<Enemy> enimiesToRem = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemy.update(dt);
            for (Bullet bullet : bullets) {
                if(bullet.cFrame.position.sub(enemy.cFrame.position, new Vector3f()).length()<bullet.cFrame.scale+enemy.cFrame.scale){
                    enemy.health -= 1;
                    bullesToRem.add(bullet);
                }
            }
            if(enemy.health<=0){
                enimiesToRem.add(enemy);
            }
        }
        for (Bullet bullet : bullets) {
            bullet.update(dt);
            if(bullet.cFrame.position.lengthSquared()>500){
                bullesToRem.add(bullet);
            }
        }
        enemies.removeAll(enimiesToRem);
        bullets.removeAll(bullesToRem);
    }

    private void processInput(double dt){
        if(shotCooldown<0.0&&glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1)==GLFW_PRESS){
            shotCooldown = 0.01;
            bullets.add(new Bullet(player, 5.0));
        }
    }

    public CFrame[] getCubes(){
        CFrame[] cframes = new CFrame[enemies.size() + bullets.size()];
        int index = 0;
        for (Enemy enemy : enemies) {
            cframes[index] = enemy.cFrame;
            index++;
        }

        for (Bullet bullet : bullets) {
            cframes[index] = bullet.getCFrame();
            index++;
        }

        return cframes;
    }
}
