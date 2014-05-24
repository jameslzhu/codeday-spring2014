package com.temporal;

import ashley.core.Component;

import com.badlogic.gdx.graphics.Texture;

public class Graphics extends Component
{
    public Texture tex;
    public Graphics(Texture texture)
    {
        tex = texture;
    }
}
