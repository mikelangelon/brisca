package com.cartamasalta.game.domain;

public class Location {
    float x;
    float y;
    boolean horizontal;
    int rotate;

    public Location(float x, float y, boolean horizontal,int rotate) {
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.rotate = rotate;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public int getRotate() {
        return rotate;
    }
}
