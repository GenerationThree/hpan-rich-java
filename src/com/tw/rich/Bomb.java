package com.tw.rich;

public class Bomb implements Tool{
    private int id;
    private int points;

    public Bomb() {
        this.id = 1;
        this.points = 50;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
