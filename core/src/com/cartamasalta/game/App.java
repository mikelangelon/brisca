package com.cartamasalta.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class App extends Game {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 480;
    private static final String SCORE_PREFERENCE = "score";

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public Preferences preferences;


    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        preferences = Gdx.app.getPreferences("Game");
        Assets.load();
        setScreen(new MenuScreen(this));
    }

    public void saveScore(long score){
        preferences.putLong(SCORE_PREFERENCE, score);
        preferences.flush();
    }

    public long getScore(){
        return preferences.getLong(SCORE_PREFERENCE);
    }
}
