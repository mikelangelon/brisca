package com.cartamasalta.game;

import com.cartamasalta.game.domain.Player;

public class GameUtils {

    public static Player[] getPlayers() {
        Player[] players = new Player[4];
        players[0] = new Player("Prota", true);
        players[1] = new Player("Juanito", false);
        players[2] = new Player("Rodrigo", false);
        players[3] = new Player("Marmota", false);
        return players;
    }
}
