package com.tw.rich.responses;

import com.tw.rich.Player;

public class BuyTollResponse implements Response {
    private int blockerId;

    public BuyTollResponse(int blockerId) {
        this.blockerId = blockerId;
    }

    @Override
    public Player.Status respond(Player player) {

        player.buyTool(blockerId);
        return Player.Status.END_TURN;
    }
}
