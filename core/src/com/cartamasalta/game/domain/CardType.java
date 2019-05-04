package com.cartamasalta.game.domain;

public enum CardType {
    OROS(0),
    COPAS(1),
    ESPADAS(2),
    BASTOS(3);

    private final int type;

    CardType(int type) {
        this.type = type;
    }

    public int getCardType() {
        return this.type;
    }
}
