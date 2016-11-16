package com.tw.rich.responses;

import com.tw.rich.Player;
import com.tw.rich.lands.Land;

public class BuyLandResponse implements Response {
    @Override
    public Player.Status respond(Player player) {
        Land currentLand = player.getCurrentLand();

        if (currentLand.getOwner() == null) {
            player.buy();
        }

        return Player.Status.END_TURN;
    }
}
