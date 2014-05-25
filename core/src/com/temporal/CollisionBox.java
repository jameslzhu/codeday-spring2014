package com.temporal;

import ashley.core.Component;
import com.badlogic.gdx.math.Polygon;

public class CollisionBox extends Component
{
  public Polygon poly;
  public CollisionBox(Polygon poly)
  {
    this.poly = poly;
  }
}
