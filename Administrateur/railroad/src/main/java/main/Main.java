package main;
import java.util.concurrent.TimeUnit;

import client.Client;

public class Main {
    public static void main(String[] args) {
        System.out.println("[LOG] START PROJECT");

        Game game = new Game();
        game.connectToReflector();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}