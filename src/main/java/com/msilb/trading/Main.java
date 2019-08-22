package com.msilb.trading;

import com.msilb.trading.csv.CandleCsvReader;
import com.msilb.trading.indicators.BreakOutStrategyIndicatorSource;
import com.msilb.trading.simulator.simulation.Simulator;
import com.msilb.trading.simulator.simulation.SimulatorImpl;
import com.msilb.trading.strategy.BreakOutStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static final double INITIAL_CASH_BALANCE = 1000000.0;

    public static void main(String[] args) {

        try {
            CandleCsvReader candleCsvReader = new CandleCsvReader("EUR.USD.csv");

            Simulator simulator = new SimulatorImpl();
            simulator.setCashBalance(INITIAL_CASH_BALANCE);

            BreakOutStrategyIndicatorSource breakOutStrategyIndicatorSource = new BreakOutStrategyIndicatorSource("EUR_USD");

            BreakOutStrategy breakOutStrategy = new BreakOutStrategy(
                    candleCsvReader,
                    breakOutStrategyIndicatorSource,
                    simulator
            );
            breakOutStrategy.init();
            breakOutStrategy.execute();

        } catch (Exception e) {
            log.error("Exception occurred while running Break-out strategy", e);
        }
    }
}
