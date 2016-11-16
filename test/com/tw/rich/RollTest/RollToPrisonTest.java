package com.tw.rich.RollTest;

import com.tw.rich.DiceInterface;
import com.tw.rich.GameMap;
import com.tw.rich.Player;
import com.tw.rich.RichMap;
import com.tw.rich.commands.Command;
import com.tw.rich.commands.RollCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.NormalLand;
import com.tw.rich.lands.Prison;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToPrisonTest {
    private static final int LEVEL_ZERO = 0;
    private static final int LEVEL_ONE = 1;
    private static final int ONE_STEP = 1;
    private static final int BLOCKER_ID = 1;
    private GameMap map;
    private DiceInterface dice;
    private Land startLand;
    private Land prison;
    private Command rollCmd;
    private Player player;

    @Before
    public void setUp() throws Exception {
        map = mock(GameMap.class);
        dice = mock(DiceInterface.class);
        startLand = mock(Land.class);
        rollCmd = new RollCommand(map, dice);

        when(dice.roll()).thenReturn(ONE_STEP);
    }


    @Test
    public void should_end_turn_when_rolling_to_prison() {
        prison = new Prison(1);
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(prison);

        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(prison));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_stay_in_prison_for_two_rounds_when_roll_to_prison() {
        startLand = new NormalLand(0);
        prison = new Prison(1);
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;
        map = new RichMap(startLand, prison);
        rollCmd = new RollCommand(map, dice);

        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(prison));
        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(prison));

        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(prison));

        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(startLand));
    }
}
