package com.tw.rich.RollTest;

import com.tw.rich.*;
import com.tw.rich.commands.Command;
import com.tw.rich.commands.RollCommand;
import com.tw.rich.lands.GiftHouse;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.TollHouse;
import com.tw.rich.responses.Response;
import com.tw.rich.responses.SelectGiftResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToGiftHouseTest {
    private static final int LEVEL_ZERO = 0;
    private static final int LEVEL_ONE = 1;
    private static final int ONE_STEP = 1;
    private static final int MONEY_GIFT_ID = 1;
    private static final int POINTS_GIFT_ID = 2;
    private static final int LUCKY_GIFT_ID = 3;
    private GameMap map;
    private DiceInterface dice;
    private Land startLand;
    private Land giftHouse;
    private Command rollCmd;
    private Response selectGiftResp;
    private Player player;
    private Tool blocker;

    @Before
    public void setUp() throws Exception {
        map = mock(GameMap.class);
        dice = mock(DiceInterface.class);
        startLand = mock(Land.class);
        blocker = new Blocker();
        rollCmd = new RollCommand(map, dice);

        when(dice.roll()).thenReturn(ONE_STEP);
    }

    @Test
    public void should_wait_for_resp_when_rolling_to_gift_house() {
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;
        giftHouse = new GiftHouse(1);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(giftHouse);

        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(giftHouse));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESP));
    }

    @Test
    public void should_select_money_gift_when_rolling_to_gift_house() {
        int INIT_MONEY = 10000;
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        player1.becomeLucky();
        player = player1;
        giftHouse = new TollHouse(blocker);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(giftHouse);
        selectGiftResp = new SelectGiftResponse(MONEY_GIFT_ID);


        player.execute(rollCmd);
        player.respond(selectGiftResp);

        assertThat(player.getMoney(), is(INIT_MONEY + 2000));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_select_points_gift_when_rolling_to_gift_house() {
        int INIT_Points = 100;
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player1.earnPoints(INIT_Points);
        player = player1;
        giftHouse = new TollHouse(blocker);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(giftHouse);
        selectGiftResp = new SelectGiftResponse(POINTS_GIFT_ID);

        player.execute(rollCmd);
        player.respond(selectGiftResp);

        assertThat(player.getPoints(), is(INIT_Points + 200));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_select_lucky_gift_when_rolling_to_gift_house() {
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;
        giftHouse = new TollHouse(blocker);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(giftHouse);
        selectGiftResp = new SelectGiftResponse(LUCKY_GIFT_ID);

        player.execute(rollCmd);
        player.respond(selectGiftResp);

        assertThat(player.isLucky(), is(true));
        assertThat(player.luckyRoundLeft(), is(5));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

//    @Test
//    public void should_fail_to_buy_toll_without_enough_money_when_rolling_to_toll_house() {
//        int INIT_POINTS = 0;
//        player = Player.createPlayerWithMapAndPoints(startLand, INIT_POINTS);
//        tollHouse = new TollHouse(blocker);
//        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(tollHouse);
//
//        player.execute(rollCmd);
//        player.respond(buyTollResp);
//
//        assertThat(player.getPoints(), is(INIT_POINTS));
//        assertThat(player.hasTool(blocker), is(false));
//        assertThat(player.getStatus(), is(Player.Status.END_TURN));
//    }
//
//    @Test
//    public void should_fail_to_buy_toll_with_tool_bag_full_when_rolling_to_toll_house() {
//        int INIT_POINTS = 100;
//        player = Player.createPlayerWithMapAndNoToolBag(startLand ,100);
//        tollHouse = new TollHouse(blocker);
//        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(tollHouse);
//
//        player.execute(rollCmd);
//        player.respond(buyTollResp);
//
//        assertThat(player.hasTool(blocker), is(false));
//        assertThat(player.getStatus(), is(Player.Status.END_TURN));
//    }


}
