package com.temporal;

import ashley.core.Entity;
import ashley.core.PooledEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Engine extends PooledEngine
{
    private Texture playerTex;
    public Engine(int windowWidth, int windowHeight)
    {
        Entity player = new Entity();
        playerTex = new Texture(Gdx.files.internal("player.png"));
        player.add(new Graphics(playerTex));
        player.add(new Position(windowWidth / 2, windowHeight / 2));

        addEntity(player);

        GraphicsSystem graphics = new GraphicsSystem(1, windowWidth, windowHeight, 1);

        addSystem(graphics);
    }

    public void dispose()
    {
        playerTex.dispose();
        getSystem(GraphicsSystem.class).dispose();
    }
}
