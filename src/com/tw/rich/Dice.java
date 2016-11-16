package com.tw.rich;

import java.util.Random;

public class Dice implements DiceInterface {
    @Override
    public int roll() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }
}
