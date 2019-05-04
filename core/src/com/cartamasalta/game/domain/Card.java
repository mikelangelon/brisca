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
    private boolean selected;

    public Card(TextureRegion image, CardType type, int value) {
        this.image = image;
        this.type = type;
        this.value = value;
    }

    public TextureRegion getImage() {
        if (isVisible()) {
            return image;
        } else {
            return Assets.cardBack;
        }
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isVisible() {
        return owner == null || selected || owner.isMainPlayer();
    }

    public CardType getType() {
        return type;
    }
}
