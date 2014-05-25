package com.temporal;

import ashley.core.Component;

public class Invincibility extends Component
{
  public boolean isInvincible;
  public double timeAccumulator;

  public Invincibility()
  {
    isInvincible = false;
    timeAccumulator = 0;
  }
}
