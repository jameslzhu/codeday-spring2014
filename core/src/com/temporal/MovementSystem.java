package com.temporal;

import ashley.core.Entity;
import ashley.core.Engine;
import ashley.core.Family;
import ashley.systems.IteratingSystem;
import ashley.core.EntityListener;
import ashley.utils.IntMap;
import ashley.signals.Signal;
import ashley.signals.Listener;

public class MovementSystem extends IteratingSystem implements Listener<Boolean>
{
  private boolean fastmo;

  public MovementSystem (int priority)
  {
    super(Family.getFamilyFor(Position.class, Velocity.class), priority);
    fastmo = false;
  }

  public void processEntity (Entity entity, float deltaTime)
  {
    Position position = entity.getComponent(Position.class);
    Velocity velocity = entity.getComponent(Velocity.class);

    if (fastmo)
    {
      position.x += velocity.x * deltaTime * 3;
      position.y += velocity.y * deltaTime * 3;
    }
    else
    {
      position.x += velocity.x * deltaTime;
      position.y += velocity.y * deltaTime;
    }
  }

  public void receive(Signal<Boolean> sig, Boolean bool)
  {
    fastmo = bool;
  }
}
