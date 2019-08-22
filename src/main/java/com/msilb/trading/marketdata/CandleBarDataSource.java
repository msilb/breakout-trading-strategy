package com.msilb.trading.marketdata;

import com.msilb.trading.model.CandleBar;

public interface CandleBarDataSource {

    /**
     * Check whether there are more candle bars in the time series
     *
     * @return true if 1 or more candle bars are available, false otherwise
     */
    boolean hasMoreCandleBars();

    /**
     * Retrieves the next candle bar
     *
     * @return next candle bar
     */
    CandleBar getNextCandleBar();
}
