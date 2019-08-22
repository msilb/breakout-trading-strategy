package com.msilb.trading.simulator.entity;

import com.msilb.trading.simulator.enumeration.TransactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Transaction {

    private long quantity;
    private double price;
    private TransactionType type;

    public double getValue() {
        return -quantity * price;
    }
}
