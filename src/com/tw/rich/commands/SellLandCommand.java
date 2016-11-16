package com.tw.rich.commands;

import com.tw.rich.GameMap;
import com.tw.rich.Player;
import com.tw.rich.commands.Command;

public class SellLandCommand implements Command {
    private GameMap map;
    private int position;

    public SellLandCommand(GameMap map, int position) {
        this.map = map;
        this.position = position;
    }

    @Override
    public Player.Status execute(Player player) {
        player.sellLand(position);
        return Player.Status.WAIT_FOR_CMD;
    }

    @Override
    public Player.Status respond(Player player) {
        return null;
    }
}
