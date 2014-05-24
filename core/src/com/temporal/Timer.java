package com.temporal;

/**
 * Basic timer class to measure time in seconds.
 */
public class Timer
{
    private long last;
    private static final double SEC_PER_MILLIS = 0.001;

    public Timer()
    {
        start();
    }

    /**
     * Starts the timer.
     */
    public void start()
    {
        last = System.currentTimeMillis();
    }

    /**
     * Returns elapsed time in seconds.
     */
    public double lap()
    {
        return (double) (System.currentTimeMillis() - last) * SEC_PER_MILLIS;
    }
}
