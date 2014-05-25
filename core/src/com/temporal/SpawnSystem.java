package com.temporal;

import java.util.Random;

import ashley.core.Entity;
import ashley.core.EntitySystem;

import com.badlogic.gdx.Gdx;

public class SpawnSystem extends EntitySystem
{
  private Random rand;
  private float spawnCooldown;
  private Engine engine;
  private Entity player;

  public SpawnSystem(int priority, Engine engine, Entity player)
  {
    super(priority);
    this.engine = engine;
    this.player = player;
    spawnCooldown = 0.0f;
    rand = new Random();
  }

  public void update(float deltaTime)
  {
    if (spawnCooldown <= 0.0f)
    {
      Position enemyPos = new Position(
          rand.nextFloat() * Gdx.graphics.getWidth(),
          rand.nextFloat() * Gdx.graphics.getHeight());
      Velocity enemyVel = new Velocity(0.0, 0.0);
      Direction enemyDir = new Direction(0.0f);

      engine.addEnemy(enemyPos, enemyVel, enemyDir);

      spawnCooldown = 2.0f;
    }
    else
    {
      spawnCooldown -= deltaTime;
    }
  }
}
