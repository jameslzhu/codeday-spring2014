package com.temporal;

import ashley.core.Entity;
import ashley.core.Engine;
import ashley.core.Family;
import ashley.core.EntitySystem;
import ashley.utils.IntMap;

public class InvincibilitySystem extends EntitySystem
{
  private Entity player;

  public InvincibilitySystem(int priority, Entity player)
  {
    super(priority);
    this.player = player;
  }

  @Override
  public void update(float deltaTime)
  {
    Invincibility inv = player.getComponent(Invincibility.class);
    if (inv.isInvincible)
    {
      inv.timeAccumulator += deltaTime;
      if (inv.timeAccumulator > 0.5)
      {
        inv.isInvincible = false;
        inv.timeAccumulator = 0.0f;
      }
    }
  }
}
