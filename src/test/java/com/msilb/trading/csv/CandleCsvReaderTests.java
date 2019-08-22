package com.msilb.trading.csv;

import com.msilb.trading.model.CandleBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CandleCsvReaderTests {

    private static final String FILE_NAME = "src/test/resources/EUR.USD-short.csv";

    private CandleCsvReader candleCsvReader;

    @BeforeEach
    void init() throws IOException {
        candleCsvReader = new CandleCsvReader(FILE_NAME);
    }

    @Test
    void shouldParseAllRows() {
        int i = 0;
        while (candleCsvReader.hasMoreCandleBars()) {
            candleCsvReader.getNextCandleBar();
            i++;
        }
        assertEquals(4, i);
    }

    @Test
    void shouldParseFirstCandleBarToBean() {
        CandleBar firstCandleBar = candleCsvReader.getNextCandleBar();
        assertEquals(LocalDate.of(2001, 1, 3), firstCandleBar.getDate());
        assertEquals(0.9507, firstCandleBar.getOpen());
        assertEquals(0.9262, firstCandleBar.getLow());
        assertEquals(0.9569, firstCandleBar.getHigh());
        assertEquals(0.9271, firstCandleBar.getClose());
    }

    @Test
    void shouldThrowErrorIfNoMoreCandleBarsCanBeParsed() {
        candleCsvReader.getNextCandleBar();
        candleCsvReader.getNextCandleBar();
        candleCsvReader.getNextCandleBar();
        candleCsvReader.getNextCandleBar();
        assertThrows(NoSuchElementException.class, () -> candleCsvReader.getNextCandleBar());
    }
}
