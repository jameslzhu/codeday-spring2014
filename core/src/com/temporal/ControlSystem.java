package com.temporal;

import ashley.core.Entity;
import ashley.core.Family;
import ashley.core.EntitySystem;
import ashley.core.EntityListener;
import ashley.utils.IntMap;
import ashley.systems.IteratingSystem;
import ashley.signals.Signal;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;

public class ControlSystem extends EntitySystem
{
    private Entity player;
    private Engine engine;
    private Signal<Boolean> signal;
    private float fireCooldown;

    public ControlSystem(int priority, Entity player, Engine engine, Signal<Boolean> signal)
    {
        super(priority);
        this.player = player;
        this.engine = engine;
        this.signal = signal;
        fireCooldown = 0.0f;
    }

    public void update (float deltaTime)
    {
        Position position = player.getComponent(Position.class);
        Velocity velocity = player.getComponent(Velocity.class);

        velocity.x = 0;
        velocity.y = 0;

        boolean fastmo = false;

        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
        {
            velocity.x = -25;
            fastmo = true;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))
        {
            velocity.x = 25;
            fastmo = true;
        }

        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
        {
            velocity.y = 25;
            fastmo = true;
        }

        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))
        {
            velocity.y = -25;
            fastmo = true;
        }

        if (fireCooldown <= 0.0f)
        {
          if (Gdx.input.isTouched())
          {
              double deltax = Gdx.input.getX() - position.x;
              double deltay = (Gdx.graphics.getHeight() - Gdx.input.getY()) - position.y;
              double length = Math.sqrt(deltax * deltax + deltay * deltay);
              deltax /= length;
              deltay /= length;

              deltax *= 100;
              deltay *= 100;

              Position bulletPos = new Position(position.x + 20.0, position.y + 20.0);
              Velocity bulletVel = new Velocity(deltax, deltay);
              PlayerBullet damage = new PlayerBullet(5);
              engine.addPlayerBullet(bulletPos, bulletVel, damage);
              fireCooldown = 0.5f;
          }
        }
        else
        {
          fireCooldown -= deltaTime;
          fastmo = true;
        }

        signal.dispatch(fastmo);
    }
}
