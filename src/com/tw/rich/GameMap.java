package com.tw.rich;

import com.tw.rich.lands.Land;

public interface GameMap {
    Land move(Land land, int step);

    void setTool(int position, Tool blocker, int distance);

    int getLandsNum();

    Land getLand(int position);

    void addLand(Land land);

    void display();
}
