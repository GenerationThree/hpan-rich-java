package com.tw.rich.RollTest;

import com.tw.rich.DiceInterface;
import com.tw.rich.GameMap;
import com.tw.rich.Player;
import com.tw.rich.commands.Command;
import com.tw.rich.commands.RollCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.Mine;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToMineTest {
    private static final int LEVEL_ZERO = 0;
    private static final int LEVEL_ONE = 1;
    private static final int ONE_STEP = 1;
    private static final int BLOCKER_ID = 1;
    private GameMap map;
    private DiceInterface dice;
    private Land startLand;
    private Land mine;
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
    public void should_end_turn_when_rolling_to_mine() {
        int minePoints = 100;
        mine = new Mine(1, minePoints);
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player = player1;
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(mine);

        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(mine));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_get_points_when_rolling_to_mine() {
        int INIT_POINTS = 100;
        int MINE_POINTS = 50;
        mine = new Mine(1, MINE_POINTS);
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        player1.earnPoints(INIT_POINTS);
        player = player1;
        when(map.move(eq(startLand), eq(ONE_STEP))).thenReturn(mine);

        player.execute(rollCmd);

        assertThat(player.getPoints(), is(INIT_POINTS + MINE_POINTS));
        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

}
