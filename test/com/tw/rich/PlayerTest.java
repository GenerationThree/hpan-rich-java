package com.tw.rich;

import com.tw.rich.commands.Command;
import com.tw.rich.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {

    private Player player;
    private Command cmd;
    private Response resp;

    @Before
    public void setUp() throws Exception {
        player = new Player(1);
        cmd = mock(Command.class);
        resp = mock(Response.class);

    }

    @Test
    public void should_wait_for_resp_after_executing_resp_requesting_command() {
        when(cmd.execute(eq(player))).thenReturn(Player.Status.WAIT_FOR_RESP);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_CMD));

        player.execute(cmd);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESP));
    }

    @Test
    public void should_wait_for_command_after_executing_other_resp_requesting_command() {
        when(cmd.execute(eq(player))).thenReturn(Player.Status.WAIT_FOR_CMD);

        player.execute(cmd);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_CMD));
    }

    @Test
    public void should_end_turn_after_responding_to_rolling_request() {
        when(cmd.execute(eq(player))).thenReturn(Player.Status.WAIT_FOR_RESP);
        when(resp.respond(eq(player))).thenReturn(Player.Status.END_TURN);

        player.execute(cmd);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_RESP));

        player.respond(resp);

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_end_turn_after_executing_non_requesting_command() {
        when(cmd.execute(eq(player))).thenReturn(Player.Status.END_TURN);

        player.execute(cmd);

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

}
