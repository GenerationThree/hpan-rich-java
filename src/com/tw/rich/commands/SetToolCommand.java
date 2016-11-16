package com.tw.rich.commands;

import com.tw.rich.GameMap;
import com.tw.rich.Player;
import com.tw.rich.Tool;

public class SetToolCommand implements Command {
    private GameMap map;
    private Tool tool;
    private int distance;

    public SetToolCommand(GameMap map, Tool tool, int distance) {
        this.map = map;
        this.tool = tool;
        this.distance = distance;
    }

    @Override
    public Player.Status execute(Player player) {
        int currentPosition = player.getCurrentLand().getPosition();
        map.setTool(currentPosition, tool, distance);

        return Player.Status.WAIT_FOR_CMD;
    }

    @Override
    public Player.Status respond(Player player) {
        return null;
    }
}
