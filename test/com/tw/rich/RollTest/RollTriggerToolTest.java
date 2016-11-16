package com.tw.rich.RollTest;

import com.tw.rich.DiceInterface;
import com.tw.rich.GameMap;
import com.tw.rich.Player;
import com.tw.rich.RichMap;
import com.tw.rich.commands.Command;
import com.tw.rich.commands.RollCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.NormalLand;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollTriggerToolTest {

    private Land start;
    private Land bombLand;
    private Land blockerLand;
    private Land land2;
    private Land hospital;

    @Before
    public void setUp() throws Exception {
        start = new NormalLand(0);
        blockerLand = NormalLand.createLandWithBlocker(1);
        bombLand = NormalLand.createLandWithBomb(1);;
        land2 = new NormalLand(2);
        hospital = NormalLand.createHospital(3);
    }

    @Test
    public void should_stay_at_blocker_when_triggering_blocker() {
        GameMap map = new RichMap(start, blockerLand, land2);
        Player player1 = new Player(1);
        player1.moveTo(start);
        Player player = player1;

        DiceInterface dice = mock(DiceInterface.class);
        when(dice.roll()).thenReturn(2);
        Command rollCmd = new RollCommand(map, dice);

        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(blockerLand));
        assertThat(blockerLand.hasBlocker(), is(false));
    }

    @Test
    public void should_go_to_hospital_when_triggering_bomb() {
        GameMap map = new RichMap(start, bombLand, land2, hospital);
        Player player1 = new Player(1);
        player1.moveTo(start);
        Player player = player1;

        DiceInterface dice = mock(DiceInterface.class);
        when(dice.roll()).thenReturn(2);
        Command rollCmd = new RollCommand(map, dice);

        player.execute(rollCmd);

        assertThat(player.getCurrentLand(), is(hospital));
        assertThat(bombLand.hasBomb(), is(false));

    }

    @Test
    public void should_stay_in_hospital_for_three_rounds_when_triggering_bomb() {
        GameMap map = new RichMap(start, bombLand, land2, hospital);
        Player player1 = new Player(1);
        player1.moveTo(start);
        Player player = player1;

        DiceInterface dice = mock(DiceInterface.class);
        when(dice.roll()).thenReturn(2);
        Command rollCmd = new RollCommand(map, dice);

        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(hospital));

        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(hospital));
        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(hospital));
        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(hospital));

        player.execute(rollCmd);
        assertThat(player.getCurrentLand(), is(bombLand));
    }
}
