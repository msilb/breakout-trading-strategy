package com.msilb.trading.strategy;

import com.msilb.trading.simulator.enumeration.Direction;
import org.junit.jupiter.api.Test;

import static com.msilb.trading.strategy.BreakOutSignalCalculator.Action;
import static com.msilb.trading.strategy.BreakOutSignalCalculator.calculate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BreakOutSignalCalculatorTests {

    @Test
    void shouldCalculateCorrectTradingSignal() {
        assertEquals(
                Action.IDLE,
                calculate(0.95, Direction.FLAT, 0.96, 0.91, 1.1)
        );
        assertEquals(
                Action.BUY,
                calculate(0.95, Direction.FLAT, 0.99, 0.96, 1.2)
        );
        assertEquals(
                Action.SELL,
                calculate(0.95, Direction.FLAT, 0.91, 0.89, 0.93)
        );
        assertEquals(
                Action.CLOSE_LONG,
                calculate(0.95, Direction.LONG, 0.94, 0.91, 0.97)
        );
        assertEquals(
                Action.CLOSE_SHORT,
                calculate(0.95, Direction.SHORT, 0.96, 0.94, 0.98)
        );
    }
}
