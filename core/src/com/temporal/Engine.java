package com.temporal;

import ashley.core.Entity;
import ashley.core.PooledEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;

public class Engine extends PooledEngine
{
    private Texture playerTex;
    private Texture playerBulletTex;
    private Texture enemyTex;
    private Texture enemyBulletTex;

    public Engine(int windowWidth, int windowHeight)
    {
        playerTex = new Texture(Gdx.files.internal("player.png"));
        playerBulletTex = new Texture(Gdx.files.internal("Player lazer.png"));
        enemyTex = new Texture(Gdx.files.internal("Fighter.png"));
        enemyBulletTex = new Texture(Gdx.files.internal("Figher lazer.png"));

        Position playerPos = new Position(windowWidth / 2, windowHeight / 2);
        Velocity playerVel = new Velocity(0.0, 0.0);
        Health health = new Health(10);
        Invincibility inv = new Invincibility();
        Entity player = addPlayer(playerPos, playerVel, health, inv);

        Position enemyPos = new Position(windowWidth / 4, windowHeight / 4);
        Velocity enemyVel = new Velocity(0.0, 0.0);
        Enemy enemyDam = new Enemy(2);
        Health enemyHealth = new Health(10);
        Entity enemy = addEnemy(enemyPos, enemyVel, enemyDam, enemyHealth);

        ControlSystem controls = new ControlSystem(0, player, this);
        MovementSystem movements = new MovementSystem(1);
        EnemyCollisionSystem enemyCollisions = new EnemyCollisionSystem(2, player, this);
        InvincibilitySystem invincible = new InvincibilitySystem(3, player);
        PlayerBulletCollisionSystem pbc = new PlayerBulletCollisionSystem(4, this);
        EnemyBulletCollisionSystem ebc = new EnemyBulletCollisionSystem(5, this, player);
        GraphicsSystem graphics = new GraphicsSystem(10, windowWidth, windowHeight, 1);

        addSystem(graphics);
        addSystem(movements);
        addSystem(enemyCollisions);
        addSystem(invincible);
        addSystem(pbc);
        addSystem(ebc);
        addSystem(controls);

        addEntity(enemy);
        addEntity(player);
        addEntityListener(pbc);
    }

    public Entity addPlayer(Position pos, Velocity vel, Health health, Invincibility invisible)
    {
        Entity player = new Entity();
        player.add(pos);
        player.add(vel);
        player.add(health);
        player.add(invisible);
        player.add(createCollisionBox(20.0f));
        player.add(new Graphics(playerTex));

        return player;
    }

    public Entity addEnemy(Position pos, Velocity vel, Enemy damage, Health health)
    {
        Entity enemy = new Entity();
        enemy.add(pos);
        enemy.add(vel);
        enemy.add(damage);
        enemy.add(health);

        float[] coords = {
              0.0f,   0.0f,
             58.0f,   0.0f,
             29.0f,  25.0f
        };
        enemy.add(new CollisionBox(new Polygon(coords)));

        enemy.add(new Graphics(enemyTex));
        return enemy;
    }

    public void addPlayerBullet(Position pos, Velocity vel, PlayerBullet damage)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(createCollisionBox(7.0f));
        bullet.add(new Graphics(playerBulletTex));
        addEntity(bullet);
    }

    public void addEnemyBullet(Position pos, Velocity vel, EnemyBullet damage)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(createCollisionBox(7.0f));
        bullet.add(new Graphics(enemyBulletTex));
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

        if (playerBulletTex != null)
        {
            playerBulletTex.dispose();
        }

        if (enemyTex != null)
        {
            enemyTex.dispose();
        }

        if (enemyBulletTex != null)
        {
            enemyBulletTex.dispose();
        }

        getSystem(GraphicsSystem.class).dispose();
    }
}
