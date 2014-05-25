package com.temporal;

import ashley.core.Entity;
import ashley.core.PooledEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Engine extends PooledEngine
{
    private Texture playerTex;
    public Engine(int windowWidth, int windowHeight)
    {
        Entity player = new Entity();
        playerTex = new Texture(Gdx.files.internal("player.png"));
        player.add(new Graphics(playerTex));
        player.add(new Position(windowWidth / 2, windowHeight / 2));

        addEntity(player);

        GraphicsSystem graphics = new GraphicsSystem(1, windowWidth, windowHeight, 1);

        addSystem(graphics);
    }

    public void addEnemy(Position pos, Velocity vel, Enemy damage, Health health, CollisionBox box)
    {
        Entity enemy = new Entity();
        enemy.add(pos);
        enemy.add(vel);
        enemy.add(damage);
        enemy.add(health);
        enemy.add(box);
        addEntity(enemy);
    }

    public void addPlayerBullet(Position pos, Velocity vel, PlayerBullet damage, CollisionBox box)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(box);
        addEntity(bullet);
    }

    public void addEnemyBullet(Position pos, Velocity vel, EnemyBullet damage, CollisionBox box)
    {
        Entity bullet = new Entity();
        bullet.add(pos);
        bullet.add(vel);
        bullet.add(damage);
        bullet.add(box);
        addEntity(bullet);
    }

    public void dispose()
    {
        playerTex.dispose();
        getSystem(GraphicsSystem.class).dispose();
    }
}
