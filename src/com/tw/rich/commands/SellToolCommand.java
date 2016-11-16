package com.tw.rich.commands;

import com.tw.rich.GameMap;
import com.tw.rich.Player;

public class SellToolCommand implements Command {
    private GameMap map;
    private int toolId;

    public SellToolCommand(GameMap map, int toolId) {
        this.map = map;
        this.toolId = toolId;
    }

    @Override
    public Player.Status execute(Player player) {
        if (player.hasTool(toolId)) {
            player.sellTool(toolId);
        }

        return Player.Status.WAIT_FOR_CMD;
    }

    @Override
    public Player.Status respond(Player player) {
        return null;
    }
}
