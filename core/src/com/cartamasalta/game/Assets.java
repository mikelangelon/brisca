package com.cartamasalta.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static TextureRegion background;
    public static Texture cards;
    public static TextureRegion cardBack;
    public static TextureRegion startButton;

    private static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        background = new TextureRegion(loadTexture("data/background.png"), 0, 0, 320, 480);
        cards = loadTexture("data/cards.png");
        cardBack = new TextureRegion(cards, 208, 319 * 4, 208, 319);

        startButton = new TextureRegion(loadTexture("data/start.png"), 0, 0, 1650, 650);
    }
}
