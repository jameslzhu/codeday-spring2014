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
        Entity player = new Entity();
        playerTex = new Texture(Gdx.files.internal("player.png"));
        Position playerPos = new Position(windowWidth / 2, windowHeight / 2);
        Velocity playerVel = new Velocity(0.0, 0.0);
        Health health = new Health(10);
        addPlayer(playerPos, playerVel, health);

        GraphicsSystem graphics = new GraphicsSystem(1, windowWidth, windowHeight, 1);

        addSystem(graphics);
    }

    public void addPlayer(Position pos, Velocity vel, Health health)
    {
        Entity player = new Entity();
        player.add(pos);
        player.add(vel);
        player.add(health);
        float size = 20.0f;
        float[] vertices = {
            0.0f, 0.0f,
            size, 0.0f,
            size, size,
            0.0f, size
        };
        Polygon shape = new Polygon(vertices);
        player.add(new CollisionBox(shape));
        player.add(new Graphics(playerTex));
        addEntity(player);
    }

    public void addEnemy(Position pos, Velocity vel, Enemy damage, Health health, CollisionBox box)
    {
        Entity enemy = new Entity();
        enemy.add(pos);
        enemy.add(vel);
        enemy.add(damage);
        enemy.add(health);
        enemy.add(box);
        enemy.add(new Graphics(enemyTex));
        addEntity(enemy);
    }

    public void addPlayerBullet(Position pos, Velocity vel, PlayerBullet damage, CollisionBox box)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(box);
        bullet.add(new Graphics(playerBulletTex));
        addEntity(bullet);
    }

    public void addEnemyBullet(Position pos, Velocity vel, EnemyBullet damage, CollisionBox box)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(box);
        bullet.add(new Graphics(enemyBulletTex));
        addEntity(bullet);
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
