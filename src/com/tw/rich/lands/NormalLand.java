package com.tw.rich.lands;

import com.tw.rich.Blocker;
import com.tw.rich.Bomb;
import com.tw.rich.Player;
import com.tw.rich.Tool;

import java.util.ArrayList;
import java.util.List;

public class NormalLand implements Land{
    private int position;
    private Player owner;
    private List<Player> players;
    private int price;
    private int level;
    private boolean hasBomb;
    private boolean hasBlocker;
    private boolean isHospital;
    private boolean isMagicHouse;

    public NormalLand(int position) {
        this.position = position;
        this.players = new ArrayList<>();
        this.hasBomb = false;
        this.hasBlocker = false;
        this.isHospital = false;
        this.isMagicHouse = false;
//        this.owner = new Player();
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void boughtBy(Player player) {
        owner = player;
    }

    @Override
    public void levelUp() {
        level++;
    }

    @Override
    public boolean hasOwner() {
        return this.owner != null;
    }

    @Override
    public int getPassingFee() {
        return level == 0 ? (price / 2) : (price * level / 2);
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
        return this.position;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public boolean hasBomb() {
        return this.hasBomb;
    }

    @Override
    public void setTool(Tool tool) {
        if (tool instanceof Blocker) {
            this.hasBlocker = true;
        }
        if (tool instanceof Bomb) {
            this.hasBomb = true;
        }
    }


    @Override
    public boolean hasBlocker() {
        return this.hasBlocker;
    }

    @Override
    public int getSellingPrice() {
        return level == 0 ? price * 2 : price * level * 2;
    }

    @Override
    public void sold() {
        price = level == 0 ? price : price / level;
        level = 0;
        owner = null;
    }

    @Override
    public void removeTool() {
        this.hasBlocker = false;
        this.hasBomb = false;
    }

    @Override
    public boolean isHospital() {
        return this.isHospital;
    }

    @Override
    public String getSymbol() {
        if (hasPlayer()) {
          return players.get(0).getName();
        }

        if (isHospital()) {
            return "H";
        }
        if (isMagicHouse()) {
            return "M";
        }
        return "0";
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

    public static Land createEmptyLandWithPriceAndLevel(int price, int level) {
        NormalLand land = new NormalLand(1);
        land.price = price;
        land.level = level;
        return land;
    }

    public static Land createOtherLandWithPriceAndLevelAndOwner(int price, int level, Player owner) {
        NormalLand land = new NormalLand(1);
        land.price = price;
        land.level = level;
        land.owner = owner;
        return land;
    }

    public static Land createLandWithPriceAndLevelAndOwnerAndPosition(int price, int level, Player owner, int position) {
        NormalLand land = new NormalLand(position);
        land.price = price;
        land.level = level;
        land.owner = owner;
        return land;
    }

    public static Land createEmptyLandWithPriceAndLevelAndPostion(int price, int level, int position) {
        NormalLand land = new NormalLand(position);
        land.price = price;
        land.level = level;
        return land;
    }

    public static Land createLandWithBlocker(int position) {
        NormalLand land = new NormalLand(position);
        land.hasBlocker = true;
        return land;
    }

    public static Land createLandWithBomb(int position) {
        NormalLand land = new NormalLand(position);
        land.hasBomb = true;
        return land;
    }

    public static Land createHospital(int position) {
        NormalLand hospital = new NormalLand(position);
        hospital.isHospital = true;
        return hospital;
    }

    public static Land createMagicHouse(int position) {
        NormalLand magicHouse = new NormalLand(position);
        magicHouse.isMagicHouse = true;
        return magicHouse;

    }

    public boolean isMagicHouse() {
        return this.isMagicHouse;
    }
}
