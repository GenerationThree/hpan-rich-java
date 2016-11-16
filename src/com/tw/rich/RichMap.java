package com.tw.rich;

import com.tw.rich.lands.Land;

import java.util.ArrayList;
import java.util.List;

public class RichMap implements GameMap {
    private List<Land> lands;

    public RichMap(Land... lands) {
        this.lands = new ArrayList<>();
        for (int i = 0; i < lands.length; i++) {
            this.lands.add(lands[i]);
        }
    }

    public void addLand(Land land) {
        this.lands.add(land);
    }

    @Override
    public void display() {
        for (int i = 0; i < 29; i++) {
            System.out.print(lands.get(i).getSymbol());
        }
        System.out.println();

        System.out.print(lands.get(69).getSymbol());
        for (int i = 0; i < 27; i++) {
            System.out.print(" ");
        }
        System.out.print(lands.get(29).getSymbol());
        System.out.println();

        System.out.print(lands.get(68).getSymbol());
        for (int i = 0; i < 27; i++) {
            System.out.print(" ");
        }
        System.out.print(lands.get(30).getSymbol());
        System.out.println();

        System.out.print(lands.get(67).getSymbol());
        for (int i = 0; i < 27; i++) {
            System.out.print(" ");
        }
        System.out.print(lands.get(31).getSymbol());
        System.out.println();

        System.out.print(lands.get(66).getSymbol());
        for (int i = 0; i < 27; i++) {
            System.out.print(" ");
        }
        System.out.print(lands.get(32).getSymbol());
        System.out.println();

        System.out.print(lands.get(65).getSymbol());
        for (int i = 0; i < 27; i++) {
            System.out.print(" ");
        }
        System.out.print(lands.get(33).getSymbol());
        System.out.println();

        System.out.print(lands.get(64).getSymbol());
        for (int i = 0; i < 27; i++) {
            System.out.print(" ");
        }
        System.out.print(lands.get(34).getSymbol());
        System.out.println();


        for (int i = 63; i > 34; i--) {
            System.out.print(lands.get(i).getSymbol());
        }
        System.out.println();


    }


    @Override
    public Land move(Land land, int step) {
        int currentPosition =  land.getPosition();
        int landsNumber = lands.size();

        for (int i = 1; i <= step; i++) {
            int nextPosition = (currentPosition + i) % landsNumber;
            Land nextLand = lands.get(nextPosition);
            if (nextLand.hasBlocker()) {
                nextLand.removeTool();
                return nextLand;
            }
            if (nextLand.hasBomb()) {
                nextLand.removeTool();
                return findHospital();
            }
        }
        return lands.get((currentPosition + step) % landsNumber);
    }

    private Land findHospital() {
        for (Land land : this.lands) {
            if (land.isHospital()) {
                return land;
            }
        }
        return null;
    }

    @Override
    public void setTool(int position, Tool tool, int distance) {
        int landsNumber = lands.size();
        int targetPosition = (position + distance) % landsNumber;

        Land targetLand = lands.get(targetPosition);

        if (!targetLand.hasPlayer()) {
            targetLand.setTool(tool);
        }
    }

    @Override
    public int getLandsNum() {
        return this.lands.size();
    }

    @Override
    public Land getLand(int position) {
        return this.lands.get(position);
    }
}
