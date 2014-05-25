package com.temporal;

import ashley.core.Entity;
import ashley.core.Engine;
import ashley.core.Family;
import ashley.systems.IteratingSystem;
import ashley.core.EntityListener;
import ashley.utils.IntMap;

public class MovementSystem extends IteratingSystem {
  public MovementSystem (int priority) {
    super(Family.getFamilyFor(Position.class, Velocity.class), priority);
  }

  public void processEntity (Entity entity, float deltaTime) {
    Position position = entity.getComponent(Position.class);
    Velocity velocity = entity.getComponent(Velocity.class);

    position.x += velocity.x * deltaTime;
    position.y += velocity.y * deltaTime;
  }
}
