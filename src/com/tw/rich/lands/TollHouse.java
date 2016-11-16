package com.tw.rich.lands;

import com.tw.rich.Player;
import com.tw.rich.Tool;

import java.util.ArrayList;
import java.util.List;

public class TollHouse implements Land {

    private int position;
    private List<Tool> tools;
    private boolean hasBlocker;
    private boolean hasBomb;
    private List<Player> players;

    public TollHouse(Tool... tools) {
        this(1, tools);
    }

    public TollHouse(int position, Tool... tools) {
        this.position = position;
        this.players = new ArrayList<>();
        this.tools = new ArrayList<>();
        for (int i = 0; i < tools.length; i++) {
            this.tools.add(tools[i]);
        }
    }

    @Override
    public List<Tool> getTools() {
        return this.tools;
    }

    @Override
    public Tool getTool(int toolId) {
        for (Tool tool: tools) {
            if (tool.getId() == toolId) {
                return tool;
            }
        }
        return null;
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public boolean hasBomb() {
        return false;
    }

    @Override
    public void setTool(Tool tool) {

    }

    @Override
    public boolean hasBlocker() {
        return false;
    }

    @Override
    public int getSellingPrice() {
        return 0;
    }

    @Override
    public void sold() {

    }

    @Override
    public void removeTool() {
        this.hasBlocker = false;
        this.hasBomb = false;
    }

    @Override
    public boolean isHospital() {
        return false;
    }

    @Override
    public String getSymbol() {
        return "T";
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public boolean hasPlayer() {
        return players.size() > 0;
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void boughtBy(Player player) {

    }

    @Override
    public void levelUp() {

    }

    @Override
    public boolean hasOwner() {
        return false;
    }

    @Override
    public int getPassingFee() {
        return 0;
    }
}
