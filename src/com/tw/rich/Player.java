package com.tw.rich;

import com.tw.rich.commands.Command;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.TollHouse;
import com.tw.rich.responses.Response;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String name;
    private Status status;
    private Land currentLand;
    private List<Land> lands;
    private int money;
    private int points;
    private boolean isLucky;
    private boolean isInPrison;
    private boolean isInHospital;
    private List<Tool> tools;
    private int maxToolNum;
    private int luckyRoundLeft;
    private int byeRoundLeft;

    public Player(int id) {
        this(id, 10000);
    }

    public Player(int id, int initMoney) {
        this.id = id;
        this.status = Status.WAIT_FOR_CMD;
        this.lands = new ArrayList<>();
        this.money = initMoney;
        this.tools = new ArrayList<>();
        this.isLucky = false;
        this.isInPrison = false;
        this.isInHospital = false;
        this.maxToolNum = 10;

        switch (id) {
            case 1:
                this.name = "Q";
                break;
            case 2:
                this.name = "A";
                break;
            case 3:
                this.name = "S";
                break;
            case 4:
                this.name = "J";
                break;
            default:
                this.name = "Q";
        }
    }

    public Status getStatus() {
        return status;
    }

    public void execute(Command cmd) {
        status = cmd.execute(this);
    }

    public void respond(Response resp) {
        status = resp.respond(this);
    }

    public Land getCurrentLand() {
        return this.currentLand;
    }

    public void moveTo(Land nextLand) {
        if (this.currentLand != null) {
            this.currentLand.removePlayer(this);
        }
        nextLand.addPlayer(this);
        this.currentLand = nextLand;
    }

    public int getMoney() {
        return this.money;
    }

    public void buy() {
        if (canPay(currentLand.getPrice())) {
            lands.add(currentLand);
            payMoney(currentLand.getPrice());
        }
    }

    public void becomeLucky() {
        isLucky = true;
        luckyRoundLeft = 5;
    }

    public void update() {
        int currentLandPrice = currentLand.getPrice();

        if (canPay(currentLandPrice)) {
            payMoney(currentLandPrice);
            currentLand.levelUp();
        }
    }

    public void payPassingFee() {
        int passingFee = currentLand.getPassingFee();
        Player currentLandOwner = currentLand.getOwner();

        if (!isLucky() && !currentLandOwner.isInPrison()) {
            payMoney(passingFee);
            currentLandOwner.earnMoney(passingFee);
        }
    }

    public void earnMoney(int price) {
        money += price;
    }

    public void payMoney(int price) {
        money -= price;
    }

    private boolean canPay(int price) {
        return money > price;
    }

    public void buyTool(int toolId) {
        Tool selected = currentLand.getTool(toolId);
        int selectedPoints = selected.getPoints();
        if (canPayPoints(selectedPoints) && isToolBagNotFull()) {
            this.tools.add(selected);
            payPoints(selected.getPoints());
        }
    }

    private boolean isToolBagNotFull() {
        return tools.size() < maxToolNum;
    }

    private void payPoints(int toolPoints) {
        points -= toolPoints;
    }

    private boolean canPayPoints(int toolPoints) {
        return points > toolPoints;
    }

    public boolean isInPrison() {
        return isInPrison;
    }

    public boolean hasTool(Tool tool) {
        return this.tools.contains(tool);
    }

    public int getPoints() {
        return points;
    }

    public void selectGift(int giftId) {
        if (giftId == 1) {
            money = money + 2000;
        }
        if (giftId == 2) {
            points += 200;
        }
        if (giftId == 3) {
            isLucky = true;
            luckyRoundLeft = 5;
        }
    }


    public boolean isLucky() {
        return this.isLucky;
    }

    public int luckyRoundLeft() {
        return this.luckyRoundLeft;
    }

    public int getByeRoundLeft() {
        return this.byeRoundLeft;
    }

    public void inprisoned() {
        isInPrison = true;
        byeRoundLeft = 2;
    }

    public void discountSpecialRound() {
        if (byeRoundLeft > 0) {
            byeRoundLeft--;
        }
    }

    public void earnPoints(int points) {
        this.points += points;
    }

    public void sellLand(int position) {
        for (Land land : lands) {
            if (land.getPosition() == position) {
                earnMoney(land.getSellingPrice());
                land.sold();
            }
        }
    }

    public int getToolNum() {
        return this.tools.size();
    }

    public boolean hasTool(int toolId) {
        for (Tool tool : this.tools) {
            if (tool.getId() == toolId) {
                return true;
            }
        }
        return false;
    }

    public void sellTool(int toolId) {
        for (Tool tool : this.tools) {
            if (tool.getId() == toolId) {
                earnPoints(tool.getPoints());
                this.tools.remove(tool);
                return;
            }
        }
    }

    public void hospitalised() {
        this.isInHospital = true;
        this.byeRoundLeft = 3;
    }

    public boolean isInHospital() {
        return this.isInHospital;
    }

    public static Player createPlayerWithRobot(Land startLand) {
        Player player = new Player(1);
        Tool robot = new Robot();
        Land toolHouse = new TollHouse(robot);
        player.moveTo(toolHouse);
        player.buyTool(3);
        player.moveTo(startLand);

        return player;
    }

    public String getName() {
        return name;
    }

    public enum Status {WAIT_FOR_RESP, END_TURN, WAIT_FOR_CMD;}

    public static Player createPlayerWithMapAndNoToolBag(Land currentLand, int points) {
        Player player = new Player(1);
        player.moveTo(currentLand);
        player.earnPoints(points);
        player.maxToolNum = 0;
        return player;
    }
}
