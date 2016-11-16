package com.tw.rich;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DiceTest {
    @Test
    public void should_dice_roll_one_to_six() {
        Dice dice = new Dice();
        List<Integer> steps = new ArrayList<>();
        steps.addAll(Arrays.asList(1,2,3,4,5,6));

        for (int i=0; i< 100; i++) {
            int step = dice.roll();
            assertThat(steps.contains(step), is(true));
        }
    }
}
