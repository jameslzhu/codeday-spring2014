package com.temporal;

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
    private Texture enemyBulletTex;

    private Sprite playerSprite;
    private Sprite playerBulletSprite;
    private Sprite enemySprite;
    private Sprite enemyBulletSprite;

    public Engine(int windowWidth, int windowHeight)
    {
        playerTex = new Texture(Gdx.files.internal("player.png"));
        playerBulletTex = new Texture(Gdx.files.internal("Player lazer.png"));
        enemyTex = new Texture(Gdx.files.internal("Fighter.png"));
        enemyBulletTex = new Texture(Gdx.files.internal("Figher lazer.png"));

        playerSprite = new Sprite(playerTex);
        playerBulletSprite = new Sprite(playerBulletTex);
        enemySprite = new Sprite(enemyTex);
        enemyBulletSprite = new Sprite(enemyBulletTex);

        enemySprite.setOrigin(29.0f, 12.5f);

        Position playerPos = new Position(windowWidth / 2, windowHeight / 2);
        Velocity playerVel = new Velocity(0.0, 0.0);
        Health health = new Health(10);
        Invincibility inv = new Invincibility();
        Entity player = addPlayer(playerPos, playerVel, health, inv);

        Position enemyPos = new Position(windowWidth / 4, windowHeight / 4);
        Velocity enemyVel = new Velocity(0.0, 0.0);
        Direction enemyDir = new Direction(0.0, 1.0);
        Enemy enemyComp = new Enemy(2, Enemy.SHOOTER);
        Health enemyHealth = new Health(100000000);
        Entity enemy = addEnemy(enemyPos, enemyVel, enemyDir, enemyComp, enemyHealth);

        Signal<Boolean> signal = new Signal<Boolean>();

        ControlSystem controls = new ControlSystem(0, player, this, signal);
        AiSystem ai = new AiSystem(1, this, player);
        MovementSystem movements = new MovementSystem(2);
        EnemyCollisionSystem enemyCollisions = new EnemyCollisionSystem(3, player, this);
        InvincibilitySystem invincible = new InvincibilitySystem(4, player);
        PlayerBulletCollisionSystem pbc = new PlayerBulletCollisionSystem(5, this);
        EnemyBulletCollisionSystem ebc = new EnemyBulletCollisionSystem(6, this, player);
        GraphicsSystem graphics = new GraphicsSystem(10, windowWidth, windowHeight, 1);

        signal.add(movements);

        addSystem(graphics);
        addSystem(movements);
        addSystem(enemyCollisions);
        addSystem(invincible);
        addSystem(pbc);
        addSystem(ebc);
        addSystem(controls);
        addSystem(ai);

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

    public Entity addEnemy(Position pos, Velocity vel, Direction dir, Enemy damage, Health health)
    {
        Entity enemy = new Entity();
        enemy.add(pos);
        enemy.add(vel);
        enemy.add(dir);
        enemy.add(damage);
        enemy.add(health);

        float[] coords = {
              0.0f,   0.0f,
             58.0f,   0.0f,
             29.0f,  25.0f
        };
        Polygon poly = new Polygon(coords);
        poly.setOrigin(29.0f, 12.5f);
        enemy.add(new CollisionBox(poly));

        enemy.add(new Graphics(enemySprite));
        return enemy;
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
