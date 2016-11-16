package com.tw.rich;

import com.tw.rich.commands.Command;
import com.tw.rich.commands.SellToolCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.TollHouse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SellTollCommandTest {
    private static final int HUNDRED_POINTS = 100;
    private static final int BOMB_ID = 1;

    @Test
    public void should_sell_tool_when_having_it() {
        Tool bomb = new Bomb();
        Land start = new TollHouse(bomb);
        GameMap map = new RichMap(start);
        Player player1 = new Player(1);
        player1.moveTo(start);
        player1.earnPoints(HUNDRED_POINTS);
        Player player = player1;
        Command sellToolCmd = new SellToolCommand(map, BOMB_ID);

        player.buyTool(BOMB_ID);
        assertThat(player.getToolNum(), is(1));

        player.execute(sellToolCmd);

        assertThat(player.getPoints(), is(HUNDRED_POINTS));
        assertThat(player.getToolNum(), is(0));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_CMD));
    }

    @Test
    public void should_not_sell_tool_when_not_having_it() {
        Tool bomb = new Bomb();
        Land start = new TollHouse(bomb);
        GameMap map = new RichMap(start);
        Player player1 = new Player(1);
        player1.moveTo(start);
        player1.earnPoints(HUNDRED_POINTS);
        Player player = player1;
        Command sellToolCmd = new SellToolCommand(map, BOMB_ID);

        player.buyTool(BOMB_ID);
        assertThat(player.getToolNum(), is(1));
        assertThat(player.getPoints(), is(HUNDRED_POINTS - 50));


        player.execute(sellToolCmd);
        assertThat(player.getPoints(), is(HUNDRED_POINTS));

        player.execute(sellToolCmd);

        assertThat(player.getPoints(), is(HUNDRED_POINTS));
        assertThat(player.getToolNum(), is(0));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_CMD));
    }
}
