package com.tw.rich;

import com.tw.rich.commands.Command;
import com.tw.rich.commands.RollCommand;
import com.tw.rich.lands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RichGameController {

    List<Player> players;
    Player currentPlayer;
    GameMap map;

    public RichGameController(List<Player> players) {
        Land start = new NormalLand(0);
        this.map = new RichMap(start);

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

        map.addLand(NormalLand.createMagicHouse(63));

        map.addLand(new Mine(64, 20));
        map.addLand(new Mine(65, 80));
        map.addLand(new Mine(66, 100));
        map.addLand(new Mine(67, 40));
        map.addLand(new Mine(68, 80));
        map.addLand(new Mine(69, 60));

        this.players = new ArrayList<>();
        for (Player player: players) {
            player.moveTo(start);
            this.players.add(player);
        }
        this.currentPlayer = this.players.get(0);
    }

    private void run() {
        Dice dice = new Dice();
        Command rollCmd = new RollCommand(map, dice);

        map.display();

        System.out.println(currentPlayer.getName() + ":");
        Scanner input = new Scanner(System.in);
        String command = input.next().toLowerCase();

        switch (command) {
            case "roll":
                currentPlayer.execute(rollCmd);
                break;
            default:
                break;
        }

        if (currentPlayer.getStatus().equals(Player.Status.WAIT_FOR_RESP)) {
            System.out.println(currentPlayer.getName() + ":");
            String response = input.next().toLowerCase();



        }

        map.display();

    }

    private Player nextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        return players.get(nextIndex);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to rich game.\n");
        Scanner input = new Scanner(System.in);

        int initMoney;
        System.out.println("Please input initial money: (1000 ~ 50000)");
        initMoney = input.nextInt();
        while (initMoney < 1000 || initMoney > 50000) {
            System.out.println("Please input valid money: (1000 ~ 50000)");
            initMoney = input.nextInt();
        }

        List<Player> players = new ArrayList<>();
        System.out.println("Please select players: (1,2,3,4)");
        String[] playerIds = input.next().split(",");
        for (String id : playerIds) {
            players.add(new Player(Integer.valueOf(id), initMoney));
        }

        RichGameController controller = new RichGameController(players);

        controller.run();
    }
}
