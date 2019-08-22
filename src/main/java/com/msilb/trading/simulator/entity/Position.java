package com.msilb.trading.simulator.entity;

import com.msilb.trading.simulator.enumeration.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Position {

    private long quantity;
    private double cost;
    private double realizedPL;

    public double getAveragePrice() {

        return getCost() / getQuantity();
    }

    public Direction getDirection() {

        if (getQuantity() < 0) {
            return Direction.SHORT;
        } else if (getQuantity() > 0) {
            return Direction.LONG;
        } else {
            return Direction.FLAT;
        }
    }
}
