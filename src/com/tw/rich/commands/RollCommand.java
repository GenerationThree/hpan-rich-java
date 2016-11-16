package com.tw.rich.commands;

import com.tw.rich.*;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.Mine;
import com.tw.rich.lands.Prison;

public class RollCommand implements Command {
    private GameMap map;
    private DiceInterface dice;

    public RollCommand(GameMap map, DiceInterface dice) {
        this.map = map;
        this.dice = dice;
    }

    @Override
    public Player.Status execute(Player player) {
        Land nextLand;
        if ((player.getCurrentLand() instanceof Prison || player.getCurrentLand().isHospital()) && player.getByeRoundLeft() != 0) {
            nextLand = player.getCurrentLand();

        } else {
            nextLand = map.move(player.getCurrentLand(), dice.roll());
        }
        player.discountSpecialRound();
        player.moveTo(nextLand);

        Land currentLand = player.getCurrentLand();

        if (currentLand.isHospital() && !player.isInHospital()) {
            player.hospitalised();
            return Player.Status.END_TURN;
        }

        if (currentLand instanceof Mine) {
            player.earnPoints(currentLand.getPoints());
            return Player.Status.END_TURN;
        }

        if (currentLand instanceof Prison && !player.isInPrison()) {
            player.inprisoned();
            return Player.Status.END_TURN;
        }

        if (!currentLand.hasOwner() ||  currentLand.getOwner().equals(player)) {
            return Player.Status.WAIT_FOR_RESP;
        } else {
            player.payPassingFee();
            return Player.Status.END_TURN;
        }

    }

    @Override
    public Player.Status respond(Player player) {
        return Player.Status.END_TURN;
    }
}
