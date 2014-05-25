package com.temporal;

import ashley.core.Entity;
import ashley.core.Family;
import ashley.core.EntitySystem;
import ashley.core.EntityListener;
import ashley.utils.IntMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;

public class ControlSystem extends EntitySystem
{
    private Engine engine;
    public ControlSystem(int priority)
    {
        super(priority);
    }

    public void processEntity (Entity entity, float deltaTime)
    {
        Position position = entity.getComponent(Position.class);
        Velocity velocity = entity.getComponent(Velocity.class);

        velocity.x = 0;
        velocity.y = 0;

        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
        {
            velocity.x = -1;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))
        {
            velocity.x = 1;
        }

        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
        {
            velocity.y = 1;
        }

        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))
        {
            velocity.y = -1;
        }

        if (Gdx.input.isTouched())
        {
            double deltax = Gdx.input.getX() - position.x;
            double deltay = Gdx.input.getY() - position.y;
            double length = Math.sqrt(deltax * deltax + deltay * deltay);
            deltax /= length;
            deltay /= length;

            deltax *= 3;
            deltay *= 3;

            Position bulletPos = new Position(position.x, position.y);
            Velocity bulletVel = new Velocity(deltax, deltay);
            PlayerBullet damage = new PlayerBullet(5);
            engine.addPlayerBullet(bulletPos, bulletVel, damage);
        }
    }
}
