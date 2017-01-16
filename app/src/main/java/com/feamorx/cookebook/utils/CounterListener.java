package com.feamorx.cookebook.utils;

/**
 * Created by x86 on 09.01.2017.
 */

public class CounterListener implements Runnable {
    private int counter;

    public CounterListener(int counter) {
        if (counter < 0) {
            throw new IllegalArgumentException("Counter can't be less zero, counter = "+counter);
        }
        this.counter = counter;
    }

    public void onComplete() {

    }

    @Override
    public void run() {
        synchronized (this) {
            counter--;
            if (counter == 0) {
                onComplete();
            }
        }
    }
}
