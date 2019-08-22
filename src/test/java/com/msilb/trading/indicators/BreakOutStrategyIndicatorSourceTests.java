package com.msilb.trading.indicators;

import com.msilb.trading.model.CandleBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BreakOutStrategyIndicatorSourceTests {

    private static final String TIME_SERIES_NAME = "EUR-USD-short";

    private BreakOutStrategyIndicatorSource breakOutStrategyIndicatorSource;

    @BeforeEach
    void init() {
        breakOutStrategyIndicatorSource = new BreakOutStrategyIndicatorSource(TIME_SERIES_NAME);
    }

    @Test
    void shouldCalculateCorrectIndicatorValues() {
        CandleBar firstCandleBar = new CandleBar();
        firstCandleBar.setDate(LocalDate.of(2003, 1, 3));
        firstCandleBar.setOpen(0.9507);
        firstCandleBar.setLow(0.9262);
        firstCandleBar.setHigh(0.9569);
        firstCandleBar.setClose(0.9271);
        CandleBar secondCandleBar = new CandleBar();
        secondCandleBar.setDate(LocalDate.of(2003, 1, 4));
        secondCandleBar.setOpen(0.9271);
        secondCandleBar.setLow(0.9269);
        secondCandleBar.setHigh(0.9515);
        secondCandleBar.setClose(0.9507);
        CandleBar thirdCandleBar = new CandleBar();
        thirdCandleBar.setDate(LocalDate.of(2003, 1, 5));
        thirdCandleBar.setOpen(0.9507);
        thirdCandleBar.setLow(0.9464);
        thirdCandleBar.setHigh(0.9591);
        thirdCandleBar.setClose(0.9575);
        CandleBar fourthCandleBar = new CandleBar();
        fourthCandleBar.setDate(LocalDate.of(2003, 1, 8));
        fourthCandleBar.setOpen(0.9583);
        fourthCandleBar.setLow(0.9462);
        fourthCandleBar.setHigh(0.9588);
        fourthCandleBar.setClose(0.9467);

        breakOutStrategyIndicatorSource.update(firstCandleBar);
        breakOutStrategyIndicatorSource.update(secondCandleBar);
        breakOutStrategyIndicatorSource.update(thirdCandleBar);
        breakOutStrategyIndicatorSource.update(fourthCandleBar);

        assertEquals(0.9455, breakOutStrategyIndicatorSource.getCurrentSma());
        assertEquals(0.9228938061584884, breakOutStrategyIndicatorSource.getCurrentBbLower());
        assertEquals(0.9681061938415116, breakOutStrategyIndicatorSource.getCurrentBbUpper());
    }
}
