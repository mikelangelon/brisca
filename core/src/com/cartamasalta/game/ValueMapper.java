package com.cartamasalta.game;

import com.cartamasalta.game.domain.Card;

import java.util.HashMap;
import java.util.Map;

public class ValueMapper {

    private static Map<Integer, Integer> pointsMap = new HashMap<Integer, Integer>() {{
        put(1, 11);
        put(3, 10);
        put(12, 4);
        put(11, 3);
        put(10, 2);
    }};

    private static Map<Integer, Integer> valueMap = new HashMap<Integer, Integer>() {{
        put(1, 20);
        put(3, 19);
    }};

    public static Integer getPoints(Card card){
        Integer points = pointsMap.get(card.getValue());
        if (points == null) {
            return 0;
        }
        return points;
    }

    public static Integer getValue(Card card){
        Integer value = valueMap.get(card.getValue());
        if (value == null) {
            return card.getValue();
        }
        return value;
    }


}
