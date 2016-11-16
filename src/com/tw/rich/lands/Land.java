package com.tw.rich.lands;

import com.tw.rich.Player;
import com.tw.rich.Tool;

import java.util.List;

public interface Land {
    Player getOwner();

    int getLevel();

    int getPrice();

    void boughtBy(Player player);

    void levelUp();

    boolean hasOwner();

    int getPassingFee();

    List<Tool> getTools();

    Tool getTool(int toolId);

    int getPosition();

    int getPoints();

    boolean hasBomb();

    void setTool(Tool tool);

    boolean hasBlocker();

    int getSellingPrice();

    void sold();

    void removeTool();

    boolean isHospital();

    String getSymbol();

    void removePlayer(Player player);

    void addPlayer(Player player);

    boolean hasPlayer();
}
