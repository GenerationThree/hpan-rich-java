package com.tw.rich;

import com.tw.rich.commands.Command;
import com.tw.rich.commands.SetToolCommand;
import com.tw.rich.commands.UseRobotCommand;
import com.tw.rich.lands.Land;
import com.tw.rich.lands.NormalLand;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UseToolTest {
    @Test
    public void should_set_tool_on_map() {
        Land startLand = new NormalLand(0);
        Land firstLand = new NormalLand(1);
        Land secondLand = new NormalLand(2);
        Land thirdLand = new NormalLand(3);

        GameMap map = new RichMap(startLand, firstLand, secondLand);
        Player player1 = new Player(1);
        player1.moveTo(startLand);
        Player player = player1;
        Tool blocker = new Blocker();
        Tool bomb = new Bomb();

        Command setBlockerCmd = new SetToolCommand(map, blocker, 1);

        player.execute(setBlockerCmd);

        assertThat(firstLand.hasBlocker(), is(true));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_CMD));

        Command setBombCmd = new SetToolCommand(map, bomb, 2);

        player.execute(setBombCmd);

        assertThat(secondLand.hasBomb(), is(true));
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_CMD));
    }

    @Test
    public void should_not_set_tool_on_player() {
        Land startLand = new NormalLand(0);
        Land firstLand = new NormalLand(1);
        Land secondLand = new NormalLand(2);

        GameMap map = new RichMap(startLand, firstLand, secondLand);
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        Player player3 = new Player(3);
        player1.moveTo(startLand);
        player2.moveTo(firstLand);
        player3.moveTo(secondLand);

        Tool blocker = new Blocker();
        Tool bomb = new Bomb();

        Command setBlockerCmd = new SetToolCommand(map, blocker, 1);

        player1.execute(setBlockerCmd);

        assertThat(firstLand.hasBlocker(), is(false));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_CMD));

        Command setBombCmd = new SetToolCommand(map, bomb, 2);

        player1.execute(setBombCmd);

        assertThat(secondLand.hasBomb(), is(false));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_CMD));
    }

    @Test
    public void should_clear_map_nearby_when_using_robot() {
        Land startLand = new NormalLand(0);
        Land firstLand = new NormalLand(1);
        Land secondLand = NormalLand.createLandWithBlocker(2);
        Land thirdLand = NormalLand.createLandWithBomb(3);

        GameMap map = new RichMap(startLand, firstLand, secondLand, thirdLand);

        Player player = Player.createPlayerWithRobot(startLand);
        Command useRobotCmd = new UseRobotCommand(map);

        player.execute(useRobotCmd);

        assertThat(secondLand.hasBlocker(), is(false));
        assertThat(thirdLand.hasBomb(), is(false));


    }
}
