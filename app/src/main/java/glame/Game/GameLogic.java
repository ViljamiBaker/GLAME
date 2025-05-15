package glame.game;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Vector3f;

import glame.game.util.CFrame;
import glame.renderer.Renderer;

public class GameLogic {
    public static Player player = new Player();

    static long window;

    static double timeSinceLastSpawn = 100000;

    static ArrayList<Enemy> enemies = new ArrayList<>();
    static ArrayList<Bullet> bullets = new ArrayList<>();

    static Random r = new Random();

    public static void init(){
        window = Renderer.window;
        player = new Player();
    }

    public static void update(){
        player.processInput();
        double dt = Renderer.deltaTime;
        processInput(dt);
        if(timeSinceLastSpawn > 1){
            timeSinceLastSpawn = 0;
            enemies.add(new Enemy(new CFrame(new Vector3f((float)r.nextDouble(-25, 25),(float)r.nextDouble(-25, 25),(float)r.nextDouble(-25, 25)),
                    0,0,0,1), 10, r.nextFloat(0.5f, 2), player.cframe.position));
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

    private static void processInput(double dt){
    }

    public static CFrame[] getCubes(){
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
