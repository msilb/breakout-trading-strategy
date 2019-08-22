package com.msilb.trading.simulator.simulation;

import com.msilb.trading.simulator.entity.Position;
import com.msilb.trading.simulator.entity.Transaction;
import com.msilb.trading.simulator.entity.trade.MarketOrder;
import com.msilb.trading.simulator.enumeration.Side;
import com.msilb.trading.simulator.enumeration.TransactionType;
import com.msilb.trading.simulator.util.PositionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimulatorImpl implements Simulator {

    private double cashBalance;
    private double price;
    private Position position;

    public void clear() {
        this.cashBalance = 0.0;
        this.price = 0.0;
        this.position = null;
    }

    public void setCashBalance(double amount) {
        this.cashBalance = amount;
    }

    public double getCashBalance() {
        return this.cashBalance;
    }

    public void setCurrentPrice(double price) {
        this.price = price;
    }

    public Position getPosition() {
        return this.position;
    }

    public void sendOrder(MarketOrder order) {

        TransactionType transactionType = Side.BUY.equals(order.getSide()) ? TransactionType.BUY : TransactionType.SELL;
        long quantity = Side.BUY.equals(order.getSide()) ? order.getQuantity() : -order.getQuantity();

        Transaction transaction = new Transaction();
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setType(transactionType);

        if (this.position == null) {

            // create a new position if necessary
            this.position = PositionUtil.processFirstTransaction(transaction);

        } else {

            // process the transaction (adjust quantity, cost and realizedPL)
            PositionUtil.processTransaction(this.position, transaction);
        }

        // add the amount to the corresponding cashBalance
        this.cashBalance = this.cashBalance + transaction.getValue();

        log.debug("executed transaction: " + transaction);
    }
}
