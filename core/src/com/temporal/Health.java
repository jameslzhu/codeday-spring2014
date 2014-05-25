package com.temporal;

import ashley.core.Component;

public class Health extends Component
{
    public int current;
    public int max;

    public Health(int maxHealth)
    {
        current = max = maxHealth;
    }
}
