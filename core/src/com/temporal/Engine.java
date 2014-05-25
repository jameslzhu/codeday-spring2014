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

        ControlSystem controls = new ControlSystem(0, player, this);
        MovementSystem movements = new MovementSystem(1);
        EnemyCollisionSystem enemyCollisions = new EnemyCollisionSystem(2, player, this);
        InvincibilitySystem invisibles = new InvincibilitySystem(3, player);
        GraphicsSystem graphics = new GraphicsSystem(10, windowWidth, windowHeight, 1);

        addSystem(graphics);
        addSystem(movements);
        addSystem(enemyCollisions);
        addSystem(invisibles);
        addSystem(controls);
    }

    public Entity addPlayer(Position pos, Velocity vel, Health health, Invincibility invisible)
    {
        Entity player = new Entity();
        player.add(pos);
        player.add(vel);
        player.add(health);
        player.add(invisible);
        float size = 20.0f;
        float[] vertices = {
            0.0f, 0.0f,
            size, 0.0f,
            size, size,
            0.0f, size
        };
        Polygon shape = new Polygon(vertices);
        player.add(createCollisionBox(20.0f));
        player.add(new Graphics(playerTex));
        addEntity(player);

        return player;
    }

    public void addEnemy(Position pos, Velocity vel, Enemy damage, Health health)
    {
        Entity enemy = new Entity();
        enemy.add(pos);
        enemy.add(vel);
        enemy.add(damage);
        enemy.add(health);
        enemy.add(createCollisionBox(20.0f));
        enemy.add(new Graphics(enemyTex));
        addEntity(enemy);
    }

    public void addPlayerBullet(Position pos, Velocity vel, PlayerBullet damage)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(createCollisionBox(4.0f));
        bullet.add(new Graphics(playerBulletTex));
        addEntity(bullet);
    }

    public void addEnemyBullet(Position pos, Velocity vel, EnemyBullet damage)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(createCollisionBox(4.0f));
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
