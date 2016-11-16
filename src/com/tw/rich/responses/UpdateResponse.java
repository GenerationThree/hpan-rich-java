package com.tw.rich.responses;

import com.tw.rich.Player;

public class UpdateResponse implements Response {
    @Override
    public Player.Status respond(Player player) {
        player.update();
        return Player.Status.END_TURN;
    }
}
