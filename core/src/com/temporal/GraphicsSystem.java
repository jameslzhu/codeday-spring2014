package com.temporal;

import ashley.core.Entity;
import ashley.core.Engine;
import ashley.core.Family;
import ashley.core.EntitySystem;
import ashley.utils.IntMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraphicsSystem extends EntitySystem
{
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private IntMap<Entity> entities;
    private double scale;

    public GraphicsSystem(int priority, int windowWidth, int windowHeight, double scaling)
    {
        super(priority);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, windowWidth, windowHeight);
        batch = new SpriteBatch();
        scale = scaling;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        entities = engine.getEntitiesFor(Family.getFamilyFor(Graphics.class, Position.class));
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        entities = null;
    }

    @Override
    public void update(float deltaTime)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);

        IntMap.Keys keys = entities.keys();
        while (keys.hasNext)
        {
            Entity entity = entities.get(keys.next());
            Sprite sprite = entity.getComponent(Graphics.class).sprite;
            Position position = entity.getComponent(Position.class);

            if (entity.hasComponent(Direction.class))
            {
                Direction dir = entity.getComponent(Direction.class);
                sprite.setRotation(dir.angle);
            }
            drawTexture(sprite, (int) (position.x * scale), (int) (position.y * scale));
        }
    }

    private void drawTexture(Sprite sprite, int x, int y)
    {
        batch.begin();
        sprite.setCenterX(x);
        sprite.setCenterY(y);
        sprite.draw(batch);
        //batch.draw(sprite, x, y);
        batch.end();
    }

    public void dispose()
    {
        batch.dispose();
    }
}
