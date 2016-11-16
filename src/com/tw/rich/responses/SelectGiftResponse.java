package com.tw.rich.responses;

import com.tw.rich.Player;

public class SelectGiftResponse implements Response {
    private int giftId;

    public SelectGiftResponse(int giftId) {
        this.giftId = giftId;
    }

    @Override
    public Player.Status respond(Player player) {
        player.selectGift(giftId);
        return Player.Status.END_TURN;
    }
}
