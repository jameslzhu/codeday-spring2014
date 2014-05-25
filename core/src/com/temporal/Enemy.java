package com.temporal;

import ashley.core.Component;

public class Enemy extends Component
{
  public int playerHealthHit;

  public Enemy(int healthHit)
  {
    this.playerHealthHit = healthHit;
  }
}
