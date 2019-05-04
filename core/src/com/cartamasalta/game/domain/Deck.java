package com.cartamasalta.game.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cartamasalta.game.App;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    public static final Rectangle position = new Rectangle(
            App.WIDTH / 2 - Card.CARD_WIDTH / 2 - 25,
            App.HEIGHT - Card.CARD_HEIGHT - 200,
            Card.CARD_WIDTH,
            Card.CARD_HEIGHT);

    int counter = 0;
    final ArrayList<Card> cards = new ArrayList<Card>();

    public Deck(Texture texture){
        initDeck(texture);
    }

    public void initDeck(Texture texture) {
        for (int type = 0; type < 4; type++) {
            for (int i = 0; i < 12; i++) {
                TextureRegion image = new TextureRegion(texture, i  * 208, type * 319, 208, 319);
                Card card = new Card(image, CardType.values()[type], i);

                cards.add(card);
            }
        }

        shuffle();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card takeCard() {
        Card card = cards.get(counter);
        counter++;
        return card;
    }

    public int getCounter() {
        return cards.size() - counter;
    }
}
