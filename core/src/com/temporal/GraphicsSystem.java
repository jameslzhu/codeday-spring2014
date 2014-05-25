package com.temporal;

import ashley.core.Entity;
import ashley.core.Engine;
import ashley.core.Family;
import ashley.core.EntitySystem;
import ashley.core.EntityListener;
import ashley.utils.IntMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    public void update(float deltaTime)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        IntMap.Keys keys = entities.keys();
        while (keys.hasNext)
        {
            Entity entity = entities.get(keys.next());
            Texture texture = entity.getComponent(Graphics.class).tex;
            Position position = entity.getComponent(Position.class);
            batch.draw(texture, (int) (position.x * scale), (int) (position.y * scale));
        }
        batch.end();
    }

    public void dispose()
    {
        batch.dispose();
    }
}
