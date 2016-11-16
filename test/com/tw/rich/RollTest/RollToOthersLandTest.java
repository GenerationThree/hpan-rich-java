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

public class RollToOthersLandTest {
    private static final int LEVEL_ZERO = 0;
    private static final int LEVEL_ONE = 1;
    private static final int ONE_STEP = 1;
    private GameMap map;
    private DiceInterface dice;
    private Land startLand;
    private Land otherLand;
    private Command rollCmd;
    private Response notUpdateResp;
    private Response updateResp;
    private Player player;
    private Player otherPlayer;

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
    public void should_end_turn_when_rolling_to_others_land() {
        int INIT_MONEY = 10000;
        int LAND_PRICE = 500;
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;
        Player player2 = new Player(1, INIT_MONEY);
        player2.moveTo(startLand);
        otherPlayer = player2;
        otherLand = NormalLand.createOtherLandWithPriceAndLevelAndOwner(LAND_PRICE, LEVEL_ZERO, otherPlayer);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(otherLand);

        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(otherLand));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_pay_passing_fee_when_rolling_to_others_land() {
        int INIT_MONEY = 10000;
        int LAND_PRICE = 500;

        Player player2 = new Player(1, INIT_MONEY);
        player2.moveTo(startLand);
        player = player2;
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        otherPlayer = player1;
        otherLand = NormalLand.createOtherLandWithPriceAndLevelAndOwner(LAND_PRICE, LEVEL_ZERO, otherPlayer);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(otherLand);


        player.execute(rollCmd);

        assertThat(player.getMoney(), is(INIT_MONEY - LAND_PRICE / 2));
        assertThat(otherPlayer.getMoney(), is(INIT_MONEY + LAND_PRICE / 2));

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_pay_doubled_passing_fee_when_rolling_to_others_land() {
        int INIT_MONEY = 10000;
        int LAND_PRICE = 500;

        Player player2 = new Player(1, INIT_MONEY);
        player2.moveTo(startLand);
        player = player2;
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        otherPlayer = player1;
        otherLand = NormalLand.createOtherLandWithPriceAndLevelAndOwner(LAND_PRICE, LEVEL_ONE, otherPlayer);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(otherLand);


        player.execute(rollCmd);

        assertThat(player.getMoney(), is(INIT_MONEY - LAND_PRICE / 2));
        assertThat(otherPlayer.getMoney(), is(INIT_MONEY + LAND_PRICE / 2));

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_not_pay_passing_fee_if_lucky_when_rolling_to_others_land() {
        int INIT_MONEY = 10000;
        int LAND_PRICE = 500;

        Player player2 = new Player(1, INIT_MONEY);
        player2.moveTo(startLand);
        player2.becomeLucky();
        player = player2;
        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        otherPlayer = player1;
        otherLand = NormalLand.createOtherLandWithPriceAndLevelAndOwner(LAND_PRICE, LEVEL_ZERO, otherPlayer);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(otherLand);

        player.execute(rollCmd);

        assertThat(player.getMoney(), is(INIT_MONEY));
        assertThat(otherPlayer.getMoney(), is(INIT_MONEY));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_not_pay_passing_fee_if_owner_in_prison_when_rolling_to_others_land() {
        int INIT_MONEY = 10000;
        int LAND_PRICE = 500;

        Player player1 = new Player(1, INIT_MONEY);
        player1.moveTo(startLand);
        player = player1;
        Player player2 = new Player(1, INIT_MONEY);
        player2.moveTo(startLand);
        player2.inprisoned();
        otherPlayer = player2;
        otherLand = NormalLand.createOtherLandWithPriceAndLevelAndOwner(LAND_PRICE, LEVEL_ZERO, otherPlayer);
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(otherLand);

        player.execute(rollCmd);

        assertThat(player.getMoney(), is(INIT_MONEY));
        assertThat(otherPlayer.getMoney(), is(INIT_MONEY));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }
}
