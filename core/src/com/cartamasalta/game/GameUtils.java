package com.cartamasalta.game;

import com.cartamasalta.game.domain.Card;
import com.cartamasalta.game.domain.Location;
import com.cartamasalta.game.domain.Player;

public class GameUtils {


    static final Location SOUTH = new Location(App.WIDTH / 2 - Card.CARD_WIDTH / 2, 0f,true, 0);
    static final Location WEST = new Location(Card.CARD_WIDTH / 2, App.HEIGHT / 2 - Card.CARD_HEIGHT / 2, false, 270);
    static final Location NORTH = new Location(App.WIDTH / 2 - Card.CARD_WIDTH / 2, App.HEIGHT - Card.CARD_HEIGHT, true, 180);
    static final Location EST = new Location(App.WIDTH - Card.CARD_WIDTH / 2 * 3, App.HEIGHT / 2 - Card.CARD_HEIGHT / 2, false, 90);

    public static Player[] getPlayers() {
        Player[] players = new Player[4];
        players[0] = new Player("Prota", true, SOUTH);
        players[1] = new Player("Juanito", false, WEST);
        players[2] = new Player("Rodrigo", false, NORTH);
        players[3] = new Player("Marmota", false, EST);
        return players;
    }
}