package com.cartamasalta.game.domain;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cartamasalta.game.Assets;

public class Card {

    public static final int CARD_WIDTH = 40;
    public static final int CARD_HEIGHT = 64;

    private int value;
    private CardType type;
    private TextureRegion image = Assets.cardBack;
    private Player owner;

    public Card() {}

    public Card(TextureRegion image, CardType type, int value) {
        this.image = image;
        this.type = type;
        this.value = value;
    }

    public TextureRegion getImage() {
        return image;
    }

    public int getValue() {
        return value;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
