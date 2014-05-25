package com.temporal;

import ashley.core.Entity;
import ashley.core.EntityListener;
import ashley.core.Family;
import ashley.systems.IteratingSystem;
import ashley.utils.IntMap;

import com.badlogic.gdx.math.Intersector;

public class PlayerBulletCollisionSystem extends IteratingSystem
{
    private IntMap<Entity> enemyEntities;
    private Engine engine;

    public PlayerBulletCollisionSystem(int priority, Engine engine)
    {
        super(Family.getFamilyFor(PlayerBullet.class, CollisionBox.class, Position.class), priority);
        this.engine = engine;
        enemyEntities = engine.getEntitiesFor(Family.getFamilyFor(Enemy.class, CollisionBox.class, Position.class, Health.class));
    }

    @Override
    public void processEntity(Entity entity, float deltaTime)
    {
        CollisionBox bulletBox = entity.getComponent(CollisionBox.class);
        PlayerBullet bullet = entity.getComponent(PlayerBullet.class);
        Position bulletPos = entity.getComponent(Position.class);
        bulletBox.poly.setPosition((float) bulletPos.x, (float) bulletPos.y);

        IntMap.Keys keys = enemyEntities.keys();
        while (keys.hasNext)
        {
            Entity enemy = enemyEntities.get(keys.next());
            CollisionBox enemyBox = enemy.getComponent(CollisionBox.class);
            Position enemyPos = enemy.getComponent(Position.class);
            enemyBox.poly.setPosition((float) enemyPos.x, (float) enemyPos.y);
            if (enemy.hasComponent(Direction.class))
            {
              Direction dir = enemy.getComponent(Direction.class);
              enemyBox.poly.setRotation(dir.angle);
            }

            if (Math.abs(bulletPos.x - enemyPos.x) < 50.0 &&
                Math.abs(bulletPos.y - enemyPos.y) < 50.0)
            {
                if (Intersector.overlapConvexPolygons(enemyBox.poly, bulletBox.poly))
                {
                    Health hp = enemy.getComponent(Health.class);
                    hp.current -= bullet.damage;
                    engine.removeEntity(entity);
                    
                    if (hp.current <= 0)
                    {
                        engine.removeEntity(enemy);
                    }

                    break;
                }
            }
        }
    }
}
