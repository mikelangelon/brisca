package com.cartamasalta.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen extends ScreenAdapter {

    private final App app;
    private Vector3 touchPoint = new Vector3();
    private Rectangle startButton;
    private OrthographicCamera guiCam;

    public MenuScreen(App app) {
        this.app = app;
        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        startButton = new Rectangle(320 / 2 - 80, 200 , 165, 65);
    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        app.batch.setProjectionMatrix(guiCam.combined);

        app.batch.enableBlending();
        app.batch.begin();
        app.batch.draw(Assets.startButton, startButton.x, startButton.y , startButton.width, startButton.height);
        app.batch.end();
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButton.contains(touchPoint.x, touchPoint.y)) {
                app.setScreen(new GameScreen(app));
                return;
            }
        }
    }
}
