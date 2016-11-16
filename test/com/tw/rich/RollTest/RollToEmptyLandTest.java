package com.tw.rich.RollTest;

import com.tw.rich.DiceInterface;
import com.tw.rich.GameMap;
import com.tw.rich.Player;
import com.tw.rich.commands.Command;
import com.tw.rich.commands.RollCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.responses.BuyLandResponse;
import com.tw.rich.responses.NotBuyResponse;
import com.tw.rich.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToEmptyLandTest {


    public static final int LEVEL_ZERO = 0;
    public static final int LEVEL_ONE = 1;
    public static final int ONE_STEP = 1;
    private GameMap map;
    private DiceInterface dice;
    private Land emptyLand;
    private Land startLand;
    private Land ownLand;
    private Command rollCmd;
    private Response notBuyResp;
    private Response buyResp;
    private Player player;

    @Before
    public void setUp() throws Exception {
        map = mock(GameMap.class);
        dice = mock(DiceInterface.class);
        emptyLand = mock(Land.class);
        ownLand = mock(Land.class);
        startLand = mock(Land.class);
        rollCmd = new RollCommand(map, dice);
        notBuyResp = new NotBuyResponse();
        buyResp = new BuyLandResponse();
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(emptyLand);
        when(emptyLand.getOwner()).thenReturn(null);
        when(dice.roll()).thenReturn(ONE_STEP);
    }

    @Test
    public void should_wait_for_resp_when_rolling_to_empty_land() {

        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;

        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(emptyLand));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESP));
    }

    @Test
    public void should_choose_not_to_buy_when_rolling_to_empty_land() {
        int INIT_MONEY = 10000;
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        player = player1;

        player.execute(rollCmd);
        player.respond(notBuyResp);

        assertThat(player.getMoney(), is(INIT_MONEY));
        assertThat(emptyLand.getOwner(), is(not(player)));
        assertThat(emptyLand.getLevel(), is(LEVEL_ZERO));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_choose_to_buy_with_enough_money_when_rolling_to_empty_land() {
        int INIT_MONEY = 100000;
        int LAND_PRICE = 500;
        when(emptyLand.getPrice()).thenReturn(LAND_PRICE);
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        player = player1;


        player.execute(rollCmd);
        player.respond(buyResp);

        assertThat(player.getMoney(), is(INIT_MONEY - LAND_PRICE));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_fail_to_buy_without_enough_money_when_rolling_to_empty_land() {
        int INIT_MONEY = 10;
        int LAND_PRICE = 500;
        when(emptyLand.getPrice()).thenReturn(LAND_PRICE);
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        player = player1;

        player.execute(rollCmd);
        player.respond(buyResp);

        assertThat(player.getMoney(), is(INIT_MONEY));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

}
