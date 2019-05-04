package com.cartamasalta.game.domain;

import com.badlogic.gdx.math.Rectangle;
import com.cartamasalta.game.App;

import java.util.ArrayList;

public class GarbageDeck {

    public static final Rectangle position = new Rectangle(
            App.WIDTH / 2 - Card.CARD_WIDTH / 2 + 100,
            App.HEIGHT - Card.CARD_HEIGHT - 150,
            Card.CARD_WIDTH,
            Card.CARD_HEIGHT);

    final ArrayList<Card> cards = new ArrayList<Card>();
    public ArrayList<Card> getCards() {
        return cards;
    }
}
