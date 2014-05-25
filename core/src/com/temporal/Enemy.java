package com.temporal;

import ashley.core.Component;

public class Enemy extends Component
{
    public static final int SHOOTER = 0;
    public static final int CHARGER = 1;

    public int playerHealthHit;
    public int type;
    public float cooldown;

    public Enemy(int healthHit, int type)
    {
        this.playerHealthHit = healthHit;
        this.type = type;
        this.cooldown = 0.0f;
    }
}
