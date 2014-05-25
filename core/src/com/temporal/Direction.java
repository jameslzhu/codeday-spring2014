package com.temporal;

import ashley.core.Component;

import com.badlogic.gdx.math.Vector2;

public class Direction extends Component
{
    public double x, y;
    public Direction(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public float angle()
    {
        Vector2 vec = new Vector2((float) x, (float) y);
        return vec.angle() - 90f;
    }
}
