package com.temporal;

import ashley.core.Entity;
import ashley.core.Engine;
import ashley.core.Family;
import ashley.core.EntitySystem;
//import ashley.core.EntityListener;
import ashley.utils.IntMap;

import com.badlogic.gdx.math.Intersector;

public class EnemyCollisionSystem extends EntitySystem
{
  private IntMap<Entity> enemyEntities;
  private Entity player;
  private Engine engine;

  public EnemyCollisionSystem(int priority, Entity player, Engine engine)
  {
    super(priority);
    this.player = player;
    this.engine = engine;
  }

  @Override
  public void addedToEngine(Engine engine)
  {
    enemyEntities = engine.getEntitiesFor(Family.getFamilyFor(Enemy.class, CollisionBox.class));
  }

  @Override
  public void update(float deltaTime)
  {
    IntMap.Keys keys = entities.keys();
    while (keys.hasNext)
    {
      Entity enemy = entities.get(keys.next());
      if (Intersector.overlapConvexPolygons(player.getComponent(CollisionBox.class).poly, enemy.getComponent(CollisionBox.class).poly))
      {
        // Player health decrease
        player.getComponent(Health.class) -= enemy.getComponent(Enemy.class).playerHealthHit;

        // Player invulnerable
        player.getComponent(Invincibility.class).isInvincible = true;

        // Player velocity stops
        player.getComponent(Velocity.class).x = 0;
        player.getComponent(Velocity.class).y = 0;

        // Enemy dies
        engine.removeEntity(enemy);

        break;
      }
    }
  }
}
