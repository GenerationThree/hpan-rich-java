package com.tw.rich.RollTest;

import com.tw.rich.DiceInterface;
import com.tw.rich.GameMap;
import com.tw.rich.Player;
import com.tw.rich.commands.Command;
import com.tw.rich.commands.RollCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.NormalLand;
import com.tw.rich.responses.NotUpdateResponse;
import com.tw.rich.responses.Response;
import com.tw.rich.responses.UpdateResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToOwnLandTest {
    private static final int LEVEL_ZERO = 0;
    private static final int LEVEL_ONE = 1;
    private static final int ONE_STEP = 1;
    private GameMap map;
    private DiceInterface dice;
    private Land startLand;
    private Land ownLand;
    private Command rollCmd;
    private Response notUpdateResp;
    private Response updateResp;
    private Player player;

    @Before
    public void setUp() throws Exception {
        map = mock(GameMap.class);
        dice = mock(DiceInterface.class);
        startLand = mock(Land.class);
        rollCmd = new RollCommand(map, dice);
        notUpdateResp = new NotUpdateResponse();
        updateResp = new UpdateResponse();
        when(dice.roll()).thenReturn(ONE_STEP);
    }

    @Test
    public void should_wait_for_resp_when_rolling_to_empty_land() {
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;
        ownLand = NormalLand.createEmptyLandWithPriceAndLevel(500, 0);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(ownLand);


        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(ownLand));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESP));
    }

    @Test
    public void should_choose_not_to_update_when_rolling_to_own_land() {
        int INIT_MONEY = 10000;
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        player = player1;
        ownLand = NormalLand.createEmptyLandWithPriceAndLevel(500, 0);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(ownLand);


        player.execute(rollCmd);
        player.respond(notUpdateResp);

        assertThat(player.getMoney(), is(INIT_MONEY));
        assertThat(ownLand.getLevel(), is(LEVEL_ZERO));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_choose_to_update_with_enough_money_when_rolling_to_empty_land() {
        int INIT_MONEY = 100000;
        int LAND_PRICE = 500;
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        player = player1;
        ownLand = NormalLand.createEmptyLandWithPriceAndLevel(LAND_PRICE, LEVEL_ZERO);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(ownLand);

        player.execute(rollCmd);
        player.respond(updateResp);

        assertThat(player.getMoney(), is(INIT_MONEY - LAND_PRICE));
        assertThat(ownLand.getLevel(), is(LEVEL_ONE));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_fail_to_buy_without_enough_money_when_rolling_to_empty_land() {
        int INIT_MONEY = 10;
        int LAND_PRICE = 500;
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        player = player1;
        ownLand = NormalLand.createEmptyLandWithPriceAndLevel(LAND_PRICE, LEVEL_ZERO);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(ownLand);

        player.execute(rollCmd);
        player.respond(updateResp);

        assertThat(player.getMoney(), is(INIT_MONEY));
        assertThat(ownLand.getLevel(), is(LEVEL_ZERO));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

}
