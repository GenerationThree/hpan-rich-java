package com.tw.rich.commands;

import com.tw.rich.Player;

public interface Command {
    Player.Status execute(Player player);

    Player.Status respond(Player player);

}
