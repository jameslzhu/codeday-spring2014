package com.temporal;

import ashley.core.Component;

public class Enemy extends Component
{
    public static int SHOOTER = 0;
    public static int CHARGER = 1;

    public int playerHealthHit;
    public int type;

    public Enemy(int healthHit, int type)
    {
        this.playerHealthHit = healthHit;
        this.type = type;
    }
}
