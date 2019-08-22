package com.msilb.trading.simulator.simulation;

import com.msilb.trading.simulator.entity.Position;
import com.msilb.trading.simulator.entity.trade.MarketOrder;

public interface Simulator {

    void clear();

    void setCashBalance(double amount);

    double getCashBalance();

    void setCurrentPrice(double price);

    void sendOrder(MarketOrder order);

    Position getPosition();
}
