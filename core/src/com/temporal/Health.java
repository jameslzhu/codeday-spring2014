package com.temporal;

import ashley.core.Component;

public class Health extends Component
{
    public int current;
    public int max;

    public Health(int currHealth, int maxHealth)
    {
        current = currHealth;
        max = maxHealth;
    }
}
