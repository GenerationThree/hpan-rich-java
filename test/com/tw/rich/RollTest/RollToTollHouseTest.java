package com.tw.rich.RollTest;

import com.tw.rich.*;
import com.tw.rich.commands.Command;
import com.tw.rich.commands.RollCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.TollHouse;
import com.tw.rich.responses.BuyTollResponse;
import com.tw.rich.responses.NotBuyResponse;
import com.tw.rich.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToTollHouseTest {
    private static final int LEVEL_ZERO = 0;
    private static final int LEVEL_ONE = 1;
    private static final int ONE_STEP = 1;
    private static final int BLOCKER_ID = 2;
    private GameMap map;
    private DiceInterface dice;
    private Land startLand;
    private Land tollHouse;
    private Command rollCmd;
    private Response notBuyResp;
    private Response buyTollResp;
    private Player player;
    private Tool blocker;

    @Before
    public void setUp() throws Exception {
        map = mock(GameMap.class);
        dice = mock(DiceInterface.class);
        startLand = mock(Land.class);
        blocker = new Blocker();
        rollCmd = new RollCommand(map, dice);
        notBuyResp = new NotBuyResponse();
        buyTollResp = new BuyTollResponse(BLOCKER_ID);

        when(dice.roll()).thenReturn(ONE_STEP);
    }

    @Test
    public void should_wait_for_resp_when_rolling_to_toll_house() {
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;
        tollHouse = new TollHouse();
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(tollHouse);

        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(tollHouse));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESP));
    }

    @Test
    public void should_choose_not_to_buy_toll_when_rolling_to_toll_house() {
        int INIT_POINTS = 100;
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player1.earnPoints(INIT_POINTS);
        player = player1;
        tollHouse = new TollHouse();
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(tollHouse);

        player.execute(rollCmd);
        player.respond(notBuyResp);

        assertThat(player.getPoints(), is(INIT_POINTS));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_choose_to_buy_toll_when_rolling_to_toll_house() {
        int INIT_POINTS = 100;
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player1.earnPoints(INIT_POINTS);
        player = player1;
        tollHouse = new TollHouse(blocker);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(tollHouse);

        player.execute(rollCmd);
        player.respond(buyTollResp);

        assertThat(player.getPoints(), is(INIT_POINTS - blocker.getPoints()));
        assertThat(player.hasTool(blocker), is(true));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_fail_to_buy_toll_without_enough_money_when_rolling_to_toll_house() {
        int INIT_POINTS = 0;
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player1.earnPoints(INIT_POINTS);
        player = player1;
        tollHouse = new TollHouse(blocker);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(tollHouse);

        player.execute(rollCmd);
        player.respond(buyTollResp);

        assertThat(player.getPoints(), is(INIT_POINTS));
        assertThat(player.hasTool(blocker), is(false));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_fail_to_buy_toll_with_tool_bag_full_when_rolling_to_toll_house() {
        int INIT_POINTS = 100;
        player = Player.createPlayerWithMapAndNoToolBag(startLand ,100);
        tollHouse = new TollHouse(blocker);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(tollHouse);

        player.execute(rollCmd);
        player.respond(buyTollResp);

        assertThat(player.hasTool(blocker), is(false));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

}
