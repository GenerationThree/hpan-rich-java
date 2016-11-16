package com.tw.rich.responses;

import com.tw.rich.Player;

public class NotUpdateResponse implements Response {
    @Override
    public Player.Status respond(Player player) {
        return Player.Status.END_TURN;
    }
}
