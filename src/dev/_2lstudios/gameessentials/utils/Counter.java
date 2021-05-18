package dev._2lstudios.gameessentials.utils;

public class Counter {
    private int number;

    public Counter() {
        this.number = 0;
    }

    public void add(final int number) {
        this.number += number;
    }

    public int get() {
        return this.number;
    }
}
