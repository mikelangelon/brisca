package com.cartamasalta.game.domain;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private boolean mainPlayer;
    private List<Card> cards;
    private Card selectedCard;

    private int points;
    private Label pointsLabel;

    private Player nextPlayer;

    public Player(String name, boolean mainPlayer) {
        this.name = name;
        this.mainPlayer = mainPlayer;
        this.cards = new ArrayList<Card>();

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1);
        Label label = new Label(name + ": 0", new Label.LabelStyle(font, Color.WHITE));
        this.pointsLabel = label;
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

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public boolean isMainPlayer() {
        return mainPlayer;
    }
}
