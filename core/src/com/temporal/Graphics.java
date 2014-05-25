package com.temporal;

import ashley.core.Component;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Graphics extends Component
{
    public Sprite sprite;
    public Graphics(Sprite sprite)
    {
        this.sprite = sprite;
    }
}
