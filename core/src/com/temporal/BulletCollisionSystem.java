package com.temporal;

import ashley.core.Entity;
import ashley.core.Family;
import ashley.systems.IteratingSystem;

import com.badlogic.gdx.math.Intersector;

public class BulletCollisionSystem extends IteratingSystem
{
    private Engine engine;
    private Entity player;
    
    public BulletCollisionSystem(int priority, Engine engine, Entity player)
    {
        super(Family.getFamilyFor(Bullet.class, CollisionBox.class), priority);
        this.player = player;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime)
    {
        if (!player.getComponent(Invincibility.class).isInvincible)
        {
            CollisionBox playerBox = player.getComponent(CollisionBox.class);
            Health health = player.getComponent(Health.class);

            CollisionBox bulletBox = entity.getComponent(CollisionBox.class);
            Bullet bullet = entity.getComponent(Bullet.class);

            if (Intersector.overlapConvexPolygons(playerBox.poly, bulletBox.poly))
            {
                health.current -= bullet.damage;
                engine.removeEntity(entity);
            }
        }
    }
}
