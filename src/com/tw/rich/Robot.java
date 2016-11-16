package com.tw.rich;

public class Robot implements Tool {
    private int id;
    private int points;

    public Robot() {
        this.id = 3;
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
