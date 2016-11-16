package com.tw.rich.commands;

import com.tw.rich.GameMap;
import com.tw.rich.Player;
import com.tw.rich.lands.Land;

public class UseRobotCommand implements Command {
    private GameMap map;

    public UseRobotCommand(GameMap map) {
        this.map = map;
    }

    @Override
    public Player.Status execute(Player player) {
        int currentPosition = player.getCurrentLand().getPosition();

        for (int step=1; step<10; step++) {
            currentPosition = (currentPosition + step) % map.getLandsNum();
            map.getLand(currentPosition).removeTool();
        }
        return null;
    }

    @Override
    public Player.Status respond(Player player) {
        return null;
    }
}
