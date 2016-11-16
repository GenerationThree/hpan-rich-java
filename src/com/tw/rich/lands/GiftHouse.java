package com.tw.rich.lands;

import com.tw.rich.Player;
import com.tw.rich.Tool;

import java.util.ArrayList;
import java.util.List;

public class GiftHouse implements Land {
    private int position;
    private List<Player> players;
    private boolean hasBlocker;
    private boolean hasBomb;

    public GiftHouse(int position) {
        this.position = position;
        this.players = new ArrayList<>();
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

    @Override
    public List<Tool> getTools() {
        return null;
    }

    @Override
    public Tool getTool(int toolId) {
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
        return "G";
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
        return false;
    }
}
