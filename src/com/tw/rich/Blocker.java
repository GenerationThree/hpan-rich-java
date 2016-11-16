package com.tw.rich;

public class Blocker implements Tool {
    private int id;
    private int point;

    public Blocker() {
        this.id = 2;
        this.point = 50;
    }

    @Override
    public int getPoints() {
        return this.point;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
