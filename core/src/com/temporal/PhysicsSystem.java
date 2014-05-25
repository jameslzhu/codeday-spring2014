package com.temporal;

import ashley.core.Entity;
import ashley.core.Engine;
import ashley.core.Family;
import ashley.core.EntitySystem;
//import ashley.core.EntityListener;
import ashley.utils.IntMap;

public class PhysicsSystem extends EntitySystem
{
  private IntMap<Entity> bulletEntities;
  private IntMap<Entity> enemyEntities;
  private Entity player;

  public PhysicsSystem(int priority)
  {
    super(priority);
  }

  @Override
  public void addedToEngine(Engine engine)
  {
    entities = engine.getEntitiesFor(Family.getFamilyFor(CollisionBox.class));
  }

  @Override
  public void update(float deltaTime)
  {
    IntMap.Keys keys = entities.keys();
    while (keys.hasNext)
    {
      Entity entity = entities.get(keys.next());
      Polygon
    }
  }
}
