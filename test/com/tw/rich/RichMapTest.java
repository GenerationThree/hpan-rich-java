package com.tw.rich;

import com.tw.rich.lands.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RichMapTest {

    @Test
    public void should_move_forward_on_map() {
        Land normalLand0 = new NormalLand(0);
        Land normalLand1 = new NormalLand(1);
        GameMap map = new RichMap(normalLand0, normalLand1);

        Land nextLand = map.move(normalLand0, 1);

        assertThat(nextLand, is(normalLand1));
    }

    @Test
    public void should_move_round_on_map() {
        Land normalLand0 = new NormalLand(0);
        Land normalLand1 = new NormalLand(1);
        Land normalLand2 = new NormalLand(2);
        GameMap map = new RichMap(normalLand0, normalLand1, normalLand2);

        Land nextLand = map.move(normalLand0, 4);
        assertThat(nextLand, is(normalLand1));
    }

    @Test
    public void display_map() {
        GameMap map = new RichMap(new NormalLand(0));

        for (int i = 1; i <= 13; i++) {
            map.addLand(new NormalLand(i));
        }

        map.addLand(NormalLand.createHospital(14));

        for (int i = 15; i <= 27; i++) {
            map.addLand(new NormalLand(i));
        }

        map.addLand(new TollHouse(28));

        for (int i = 29; i <= 34; i++) {
            map.addLand(new NormalLand(i));
        }

        map.addLand(new GiftHouse(35));

        for (int i = 36; i <= 48; i++) {
            map.addLand(new NormalLand(i));
        }

        map.addLand(new Prison(49));

        for (int i = 50; i <= 62; i++) {
            map.addLand(new NormalLand(i));
        }

        //to be changed to magic house
        map.addLand(NormalLand.createMagicHouse(63));

        map.addLand(new Mine(64, 20));
        map.addLand(new Mine(65, 80));
        map.addLand(new Mine(66, 100));
        map.addLand(new Mine(67, 40));
        map.addLand(new Mine(68, 80));
        map.addLand(new Mine(69, 60));

        map.display();
    }
}
