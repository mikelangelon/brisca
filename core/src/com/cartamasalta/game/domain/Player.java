package com.cartamasalta.game.domain;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private boolean you;
    private List<Card> cards;
    private int points;
    private Label pointsLabel;

    public Player(String name, boolean you) {
        this.name = name;
        this.you = you;
        this.cards = new ArrayList<Card>();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1);
        Label label = new Label(name + ": 0", new Label.LabelStyle(font, Color.WHITE));
        this.pointsLabel = label;
    }

    public void setCards(List<Card> card) {
        this.cards = card;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        card.setOwner(this);
        cards.add(card);
    }

    public String getName() {
        return name;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
        getPointsLabel().setText(name + ": " + points);
    }

    public Label getPointsLabel() {
        return pointsLabel;
    }
}
