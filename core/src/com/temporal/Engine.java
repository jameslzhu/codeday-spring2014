package com.temporal;

import java.util.Random;

import ashley.core.Entity;
import ashley.core.PooledEngine;
import ashley.signals.Signal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Engine extends PooledEngine
{
    private Texture playerTex;
    private Texture playerBulletTex;
    private Texture enemyTex;
    private Texture chargerTex;
    private Texture enemyBulletTex;

    private Sprite playerSprite;
    private Sprite playerBulletSprite;
    private Sprite enemySprite;
    private Sprite chargerSprite;
    private Sprite enemyBulletSprite;

    private Random rand;

    public Engine(int windowWidth, int windowHeight)
    {
        rand = new Random();

        playerTex = new Texture(Gdx.files.internal("player.png"));
        playerBulletTex = new Texture(Gdx.files.internal("Player lazer.png"));
        enemyTex = new Texture(Gdx.files.internal("Fighter.png"));
        chargerTex = new Texture(Gdx.files.internal("Charge.png"));
        enemyBulletTex = new Texture(Gdx.files.internal("Figher lazer.png"));

        playerSprite = new Sprite(playerTex);
        playerBulletSprite = new Sprite(playerBulletTex);
        enemySprite = new Sprite(enemyTex);
        chargerSprite = new Sprite(chargerTex);
        enemyBulletSprite = new Sprite(enemyBulletTex);

        enemySprite.setOrigin(29.0f, 12.5f);

        Position playerPos = new Position(windowWidth / 2, windowHeight / 2);
        Velocity playerVel = new Velocity(0.0, 0.0);
        Health health = new Health(10);
        Invincibility inv = new Invincibility();
        Entity player = addPlayer(playerPos, playerVel, health, inv);

        Position enemyPos = new Position(windowWidth / 4, windowHeight / 4);
        Velocity enemyVel = new Velocity(0.0, 0.0);
        Direction enemyDir = new Direction(90.0f);
        Enemy enemyComp = new Enemy(2, Enemy.SHOOTER);
        Health enemyHealth = new Health(10);
        Graphics enemyGraph= new Graphics(enemySprite);
        float[] shooterCoords = {
              0.0f,   0.0f,
             58.0f,   0.0f,
             29.0f,  25.0f
        };
        Polygon enemyPoly = new Polygon(shooterCoords);
        enemyPoly.setOrigin(29.0f, 12.5f);
        CollisionBox enemyBox = new CollisionBox(enemyPoly);
        Entity enemy = addEnemy(enemyPos, enemyVel, enemyDir, enemyComp, enemyHealth, enemyGraph, enemyBox);

        Signal<Boolean> signal = new Signal<Boolean>();

        ControlSystem controls = new ControlSystem(0, player, this, signal);
        MovementSystem movements = new MovementSystem(1);
        EnemyCollisionSystem enemyCollisions = new EnemyCollisionSystem(2, player, this);
        InvincibilitySystem invincible = new InvincibilitySystem(3, player);
        PlayerBulletCollisionSystem pbc = new PlayerBulletCollisionSystem(4, this);
        EnemyBulletCollisionSystem ebc = new EnemyBulletCollisionSystem(5, this, player);
        SpawnSystem spawns = new SpawnSystem(6, this, player);
        GraphicsSystem graphics = new GraphicsSystem(10, windowWidth, windowHeight, 1);

        signal.add(movements);

        addSystem(graphics);
        addSystem(movements);
        addSystem(enemyCollisions);
        addSystem(invincible);
        addSystem(pbc);
        addSystem(ebc);
        addSystem(controls);
        addSystem(spawns);

        addEntity(enemy);
        addEntity(player);
    }

    public Entity addPlayer(Position pos, Velocity vel, Health health, Invincibility invisible)
    {
        Entity player = new Entity();
        player.add(pos);
        player.add(vel);
        player.add(health);
        player.add(invisible);
        player.add(createCollisionBox(20.0f));
        player.add(new Graphics(playerSprite));

        return player;
    }

    public Entity addEnemy(Position pos, Velocity vel, Direction dir, Enemy damage, Health health, Graphics enemyGraph, CollisionBox box)
    {
        Entity enemy = new Entity();
        enemy.add(pos);
        enemy.add(vel);
        enemy.add(dir);
        enemy.add(damage);
        enemy.add(health);

        enemy.add(box);
        enemy.add(enemyGraph);

        return enemy;
    }
    public void addEnemy(Position pos, Velocity vel, Direction dir)
    {
      Health enemyHealth = new Health(10);
      Enemy enemyComp;
      Graphics enemyGraph;
      Polygon poly;
      float[] chargerCoords = {
           27.5f,   0.0f,
           17.0f,  40.0f,
           24.0f,  59.0f,
           31.0f,  59.0f,
           38.0f,  40.0f
      };
      float[] shooterCoords = {
            0.0f,   0.0f,
           58.0f,   0.0f,
           29.0f,  25.0f
      };

      if (rand.nextBoolean())
      {
        enemyComp = new Enemy(2, Enemy.CHARGER);
        enemyGraph = new Graphics(chargerSprite);
        poly = new Polygon(chargerCoords);
        poly.setOrigin(27.5f, 29.5f);
      }
      else
      {
        enemyComp = new Enemy(2, Enemy.SHOOTER);
        enemyGraph = new Graphics(enemySprite);
        poly = new Polygon(shooterCoords);
        poly.setOrigin(29.0f, 12.5f);
      }
      CollisionBox box = new CollisionBox(poly);
      Entity enemy = addEnemy(pos, vel, dir, enemyComp, enemyHealth, enemyGraph, box);

      addEntity(enemy);
    }

    public void addPlayerBullet(Position pos, Velocity vel, PlayerBullet damage)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(createCollisionBox(7.0f));
        bullet.add(new Graphics(playerBulletSprite));
        addEntity(bullet);
    }

    public void addEnemyBullet(Position pos, Velocity vel, EnemyBullet damage)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(createCollisionBox(7.0f));
        bullet.add(new Graphics(enemyBulletSprite));
        addEntity(bullet);
    }

    private CollisionBox createCollisionBox(float size)
    {
        float[] coords = {
            0.0f, 0.0f,
            size, 0.0f,
            size, size,
            0.0f, size
        };
        Polygon poly = new Polygon(coords);
        return new CollisionBox(new Polygon(coords));
    }

    public void dispose()
    {
        playerTex.dispose();
        playerBulletTex.dispose();
        enemyTex.dispose();
        enemyBulletTex.dispose();

        getSystem(GraphicsSystem.class).dispose();
    }
}
