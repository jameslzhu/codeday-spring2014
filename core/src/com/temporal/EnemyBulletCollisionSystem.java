package com.temporal;

import ashley.core.Entity;
import ashley.core.Family;
import ashley.systems.IteratingSystem;

import com.badlogic.gdx.math.Intersector;

public class EnemyBulletCollisionSystem extends IteratingSystem
{
    private Engine engine;
    private Entity player;
    
    public EnemyBulletCollisionSystem(int priority, Engine engine, Entity player)
    {
        super(Family.getFamilyFor(EnemyBullet.class, CollisionBox.class), priority);
        this.engine = engine;
        this.player = player;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime)
    {
        if (!player.getComponent(Invincibility.class).isInvincible)
        {
            CollisionBox playerBox = player.getComponent(CollisionBox.class);
            Position playerPos = player.getComponent(Position.class);
            playerBox.poly.setPosition((float) playerPos.x, (float) playerPos.y);

            Health health = player.getComponent(Health.class);

            CollisionBox bulletBox = entity.getComponent(CollisionBox.class);
            EnemyBullet bullet = entity.getComponent(EnemyBullet.class);
            Position bulletPos = entity.getComponent(Position.class);
            bulletBox.poly.setPosition((float) bulletPos.x, (float) bulletPos.y);

            if (Intersector.overlapConvexPolygons(playerBox.poly, bulletBox.poly))
            {
                health.current -= bullet.damage;
                engine.removeEntity(entity);

                if (health.current <= 0)
                {
                    engine.removeEntity(player);
                }
            }
        }
    }
}
