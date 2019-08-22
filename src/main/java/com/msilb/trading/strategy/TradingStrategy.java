package com.msilb.trading.strategy;

public interface TradingStrategy {

    /**
     * Initialize Trading Strategy
     */
    void init();

    /**
     * Execute Trading Strategy
     */
    void execute();
}
