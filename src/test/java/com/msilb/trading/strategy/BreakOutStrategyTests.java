package com.msilb.trading.strategy;

import com.msilb.trading.csv.CandleCsvReader;
import com.msilb.trading.indicators.BreakOutStrategyIndicatorSource;
import com.msilb.trading.simulator.simulation.Simulator;
import com.msilb.trading.simulator.simulation.SimulatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BreakOutStrategyTests {

    private static final String FILE_NAME = "src/test/resources/EUR.USD-long.csv";

    private BreakOutStrategy breakOutStrategy;
    private Simulator simulator;

    @BeforeEach
    void init() throws IOException {
        CandleCsvReader candleCsvReader = new CandleCsvReader(FILE_NAME);

        simulator = new SimulatorImpl();
        simulator.setCashBalance(1000000.0);

        BreakOutStrategyIndicatorSource breakOutStrategyIndicatorSource =
                new BreakOutStrategyIndicatorSource("EUR_USD");

        breakOutStrategy = new BreakOutStrategy(
                candleCsvReader,
                breakOutStrategyIndicatorSource,
                simulator
        );
    }

    @Test
    void shouldReturnCorrectCashBalanceAfterBacktest() {
        breakOutStrategy.init();
        breakOutStrategy.execute();
        assertEquals(983800d, simulator.getCashBalance());
    }
}
