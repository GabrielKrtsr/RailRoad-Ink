package main;

import board.Board;

import java.io.FileNotFoundException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        GameParser gameParser = new GameParser("src/main/java/main/text3.txt");
        Map<String,Game> games = gameParser.getGames();
        for(String gameId: games.keySet()){
            Game currentGame = games.get(gameId);
            int i = 1;
            System.out.println("Game: " + gameId);
            for(Round round: currentGame.getRounds()){
                System.out.println("Round " + i);
                Map<Player, Board> playerBoardMap = round.getPlayerBoardMap();
                for(Player player: playerBoardMap.keySet()){
                    System.out.println(player.getId());
                    playerBoardMap.get(player).display();
                }
                i++;
            }
            i = 1;
            System.out.println();
        }





    }
}
