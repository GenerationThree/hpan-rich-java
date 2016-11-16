package com.tw.rich;

import com.tw.rich.commands.Command;
import com.tw.rich.commands.SellLandCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.NormalLand;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SellLandCommandTest {
    @Test
    public void should_sell_players_land() {
        int LAND_PRICE = 500;
        int LEVEL_ZERO = 0;
        int INIT_MONEY = 1000;

        Land start = new NormalLand(0);
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(start);
        Player player = player1;
        Land ownLand = NormalLand.createEmptyLandWithPriceAndLevelAndPostion(LAND_PRICE, LEVEL_ZERO, 1);
        GameMap map = new RichMap(start, ownLand);

        player.moveTo(ownLand);
        player.buy();

        Command sellLandCmd = new SellLandCommand(map, 1);

        player.execute(sellLandCmd);

        assertThat(ownLand.hasOwner(), is(false));
        assertThat(player.getMoney(), is(INIT_MONEY - LAND_PRICE + LAND_PRICE * 2));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_CMD));
    }

    @Test
    public void should_fail_to_sell_others_land() {
        int LAND_PRICE = 500;
        int LEVEL_ZERO = 0;
        int INIT_MONEY = 1000;

        Land start = new NormalLand(0);
        Player player1 = new Player(1, INIT_MONEY);
        Player player2 = new Player(2);
        player1.moveTo(start);
        Land other = NormalLand.createOtherLandWithPriceAndLevelAndOwner(LAND_PRICE, LEVEL_ZERO, player2);
        GameMap map = new RichMap(start, other);

        Command sellLandCmd = new SellLandCommand(map, 1);

        player1.execute(sellLandCmd);

        assertThat(other.getOwner(), is(player2));
        assertThat(player1.getMoney(), is(INIT_MONEY));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_CMD));
    }
}
